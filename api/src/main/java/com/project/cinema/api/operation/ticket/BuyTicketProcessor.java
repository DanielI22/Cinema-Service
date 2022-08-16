package com.project.cinema.api.operation.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.ticket.BuyTicketRequest;
import com.project.cinema.api.model.response.ticket.BuyTicketResponse;
import io.vavr.control.Either;

public interface BuyTicketProcessor extends OperationProcessor<BuyTicketRequest, BuyTicketResponse> {
    @Override
    Either<Error, BuyTicketResponse> process(BuyTicketRequest operationInput);
}
