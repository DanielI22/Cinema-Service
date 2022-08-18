package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.error.projection.InvalidProjectionIdError;
import com.project.cinema.api.error.ticket.InvalidTicketTypeError;
import com.project.cinema.api.error.ticket.NoCapacityError;
import com.project.cinema.api.model.request.ticket.ReserveTicketRequest;
import com.project.cinema.api.model.response.ticket.ReserveTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.api.operation.ticket.ReserveTicketProcessor;
import com.project.cinema.core.exception.NoCapacityException;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.entity.projection.Ticket;
import com.project.cinema.data.entity.user.User;
import com.project.cinema.data.repository.user.UserRepository;
import com.project.cinema.data.ticketEnum.TicketStatus;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import com.project.cinema.data.repository.projection.TicketRepository;
import com.project.cinema.data.ticketEnum.TicketType;
import com.project.pricing.api.feign.PricingClient;
import com.project.pricing.api.model.PricingRequest;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ReserveTicketProcessorCore implements ReserveTicketProcessor {
    private final TicketRepository ticketRepository;
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;
    private final PricingClient pricingClient;
    private final UserRepository userRepository;


    public ReserveTicketProcessorCore(TicketRepository ticketRepository, ProjectionRepository projectionRepository, ConversionService conversionService, PricingClient pricingClient, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
        this.pricingClient = pricingClient;
        this.userRepository = userRepository;
    }

    @Override
    public Either<Error, ReserveTicketResponse> process(ReserveTicketRequest ticketRequest) {
       return Try.of(() -> {
            ProjectionEntity projection = projectionRepository.findById(ticketRequest.getProjectionId()).orElseThrow();
            if(projection.getCapacity() == 0) {
                throw new NoCapacityException();
            }

           final User user = userRepository
                   .findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

            Ticket ticket = new Ticket();
            ticket.setProjectionId(ticketRequest.getProjectionId());
            ticket.setUserId(user.getId());
            ticket.setType(TicketType.valueOf(ticketRequest.getTicketType().toUpperCase()));
            ticket.setTicketPrice(pricingClient
                .calculateTicketPrice(PricingRequest
                        .builder()
                        .capacity(projection.getCapacity())
                        .ticketBasePrice(projection.getTicketPrice())
                        .ticketType(ticketRequest.getTicketType())
                        .build())
                .getTicketPrice());

            ticket.setStatus(TicketStatus.RESERVED);
            ticketRepository.save(ticket);
            projection.setCapacity(projection.getCapacity()-1);
            projectionRepository.save(projection);

            return ReserveTicketResponse.builder()
                    .ticket(conversionService.convert(ticket, TicketResponse.class))
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof NoSuchElementException) {
                        return new InvalidProjectionIdError();
                    }
                    else if(throwable instanceof NoCapacityException) {
                        return new NoCapacityError();
                    }
                    else if(throwable instanceof IllegalArgumentException) {
                        return new InvalidTicketTypeError();
                    }
                    return new ServiceUnavailableError();
                });
    }
}
