package com.project.cinema.api.operation.ticket;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.response.ticket.UserTicketsResponse;
import io.vavr.control.Either;

public interface GetUserTicketsProcessor extends OperationProcessor<EmptyRequest, UserTicketsResponse> {
    @Override
    Either<Error, UserTicketsResponse> process(EmptyRequest operationInput);
}
