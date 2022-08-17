package com.project.cinema.api.operation.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.ticket.CancelTicketRequest;
import com.project.cinema.api.model.response.ticket.CancelTicketResponse;
import io.vavr.control.Either;

public interface CancelTicketProcessor extends OperationProcessor<CancelTicketRequest, CancelTicketResponse> {
    @Override
    Either<Error, CancelTicketResponse> process(CancelTicketRequest operationInput);
}
