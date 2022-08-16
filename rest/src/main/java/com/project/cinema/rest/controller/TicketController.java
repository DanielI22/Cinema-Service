package com.project.cinema.rest.controller;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.request.ticket.BuyTicketRequest;
import com.project.cinema.api.model.request.ticket.ReserveTicketRequest;
import com.project.cinema.api.model.response.ticket.BuyTicketResponse;
import com.project.cinema.api.model.response.ticket.ReserveTicketResponse;
import com.project.cinema.api.model.response.ticket.UserTicketsResponse;
import com.project.cinema.api.operation.ticket.BuyTicketProcessor;
import com.project.cinema.api.operation.ticket.GetUserTicketsProcessor;
import com.project.cinema.api.operation.ticket.ReserveTicketProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    private final ReserveTicketProcessor reserveTicketProcessor;
    private final BuyTicketProcessor buyTicketProcessor;
    private final GetUserTicketsProcessor getUserTicketsProcessor;

    public TicketController(ReserveTicketProcessor reserveTicketProcessor, BuyTicketProcessor buyTicketProcessor, GetUserTicketsProcessor getUserTicketsProcessor) {
        this.reserveTicketProcessor = reserveTicketProcessor;
        this.buyTicketProcessor = buyTicketProcessor;
        this.getUserTicketsProcessor = getUserTicketsProcessor;
    }

    @PostMapping("/ticket/reserve")
    public ResponseEntity<?> reserveTicket(@RequestBody ReserveTicketRequest reserveTicketRequest){
        Either<Error, ReserveTicketResponse> result = reserveTicketProcessor
                .process(reserveTicketRequest);

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @PostMapping("/ticket/buy")
    public ResponseEntity<?> buyTicket(@RequestBody BuyTicketRequest buyTicketRequest){
        Either<Error, BuyTicketResponse> result = buyTicketProcessor.process(buyTicketRequest);

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @GetMapping("tickets/my-tickets")
    public ResponseEntity<?> showTickets() {
        Either<Error, UserTicketsResponse> result = getUserTicketsProcessor
                .process(new EmptyRequest());

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
