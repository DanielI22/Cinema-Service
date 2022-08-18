package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.error.ticket.TicketNotFoundError;
import com.project.cinema.api.model.request.ticket.CancelTicketRequest;
import com.project.cinema.api.model.response.ticket.CancelTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.api.operation.ticket.CancelTicketProcessor;
import com.project.cinema.core.exception.TicketNotFoundException;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.entity.projection.Ticket;
import com.project.cinema.data.entity.user.User;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import com.project.cinema.data.repository.projection.TicketRepository;
import com.project.cinema.data.repository.user.UserRepository;
import com.project.cinema.data.ticketEnum.TicketStatus;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CancelTicketProcessorCore implements CancelTicketProcessor {
    private final TicketRepository ticketRepository;
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;

    public CancelTicketProcessorCore(TicketRepository ticketRepository, ProjectionRepository projectionRepository, ConversionService conversionService, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
        this.userRepository = userRepository;
    }

    @Override
    public Either<Error, CancelTicketResponse> process(CancelTicketRequest ticketRequest) {
        return Try.of(() -> {
            final Ticket ticket = ticketRepository.findById(ticketRequest.getTicketId()).orElseThrow(TicketNotFoundException::new);
            if(ticket.getStatus() == TicketStatus.CANCELED) {
                throw new IllegalArgumentException();
            }
            final User user = userRepository.findByUserName(
                    SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

            if(!Objects.equals(ticket.getUserId(), user.getId())) {
                throw new TicketNotFoundException();
            }

            final ProjectionEntity projection = projectionRepository.findById(ticket.getProjectionId()).orElseThrow();
            projection.setCapacity(projection.getCapacity()+1);
            projectionRepository.save(projection);
            ticket.setStatus(TicketStatus.CANCELED);
            ticketRepository.save(ticket);

            return CancelTicketResponse.builder()
                    .ticket(conversionService.convert(ticket, TicketResponse.class))
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof TicketNotFoundException) {
                        return new TicketNotFoundError();
                    }
                    else if(throwable instanceof IllegalArgumentException) {
                        return new TicketNotFoundError();
                    }
                    return new ServiceUnavailableError();
                });
    }
}
