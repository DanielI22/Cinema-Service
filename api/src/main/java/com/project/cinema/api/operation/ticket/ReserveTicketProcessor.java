package com.project.cinema.api.operation.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.ticket.ReserveTicketRequest;
import com.project.cinema.api.model.response.ticket.ReserveTicketResponse;
import io.vavr.control.Either;

public interface ReserveTicketProcessor extends OperationProcessor<ReserveTicketRequest, ReserveTicketResponse> {
    @Override
    Either<Error, ReserveTicketResponse> process(ReserveTicketRequest operationInput);
}
