package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.error.ticket.TicketNotFoundError;
import com.project.cinema.api.model.request.ticket.CancelTicketRequest;
import com.project.cinema.api.model.response.ticket.CancelTicketResponse;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.api.operation.ticket.CancelTicketProcessor;
import com.project.cinema.core.exception.TicketNotFoundException;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.entity.Ticket;
import com.project.cinema.data.repository.ProjectionRepository;
import com.project.cinema.data.repository.TicketRepository;
import com.project.cinema.data.ticketEnum.TicketStatus;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CancelTicketProcessorCore implements CancelTicketProcessor {
    private final TicketRepository ticketRepository;
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;

    public CancelTicketProcessorCore(TicketRepository ticketRepository, ProjectionRepository projectionRepository, ConversionService conversionService) {
        this.ticketRepository = ticketRepository;
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, CancelTicketResponse> process(CancelTicketRequest ticketRequest) {
        return Try.of(() -> {
            final Ticket ticket = ticketRepository.findById(ticketRequest.getTicketId()).orElseThrow(TicketNotFoundException::new);
            if(ticket.getStatus() == TicketStatus.CANCELED) {
                throw new IllegalArgumentException();
            }

//            if(ticket.getUserId() != currentuserid)

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
