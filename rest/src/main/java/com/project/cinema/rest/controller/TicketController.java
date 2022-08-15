package com.project.cinema.rest.controller;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.model.request.ticket.ReserveTicketRequest;
import com.project.cinema.api.model.response.ticket.ReserveTicketResponse;
import com.project.cinema.api.operation.ticket.ReserveTicketProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    private final ReserveTicketProcessor reserveTicketProcessor;

    public TicketController(ReserveTicketProcessor reserveTicketProcessor) {
        this.reserveTicketProcessor = reserveTicketProcessor;
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
}
