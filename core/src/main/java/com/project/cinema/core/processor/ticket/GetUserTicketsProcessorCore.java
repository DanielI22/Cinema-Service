package com.project.cinema.core.processor.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.error.ticket.TicketsNotFoundError;
import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.response.ticket.TicketResponse;
import com.project.cinema.api.model.response.ticket.UserTicketsResponse;
import com.project.cinema.api.operation.ticket.GetUserTicketsProcessor;
import com.project.cinema.core.exception.TicketNotFoundException;
import com.project.cinema.data.entity.Ticket;
import com.project.cinema.data.repository.TicketRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserTicketsProcessorCore implements GetUserTicketsProcessor {
    private final TicketRepository ticketRepository;
    private final ConversionService conversionService;

    public GetUserTicketsProcessorCore(TicketRepository ticketRepository, ConversionService conversionService) {
        this.ticketRepository = ticketRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, UserTicketsResponse> process(EmptyRequest operationInput) {
        return Try.of(() -> {
            final List<Ticket> tickets = ticketRepository.findAllByUserId(1L);
            if(tickets.isEmpty()) {
                throw new TicketNotFoundException();
            }
            return UserTicketsResponse
                    .builder()
                    .userId("1")
                    .tickets(tickets.stream()
                            .map(t -> conversionService.convert(t, TicketResponse.class))
                            .collect(Collectors.toList()))
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof TicketNotFoundException) {
                        return new TicketsNotFoundError();
                    }
                    return new ServiceUnavailableError();
                });
    }
}
