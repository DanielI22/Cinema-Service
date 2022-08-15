package com.project.cinema.api.operation.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.projection.GetProjectionsByDateRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByDateResponse;
import io.vavr.control.Either;

public interface GetProjectionsByDateProcessor extends OperationProcessor<GetProjectionsByDateRequest, GetProjectionsByDateResponse> {
    @Override
    Either<Error, GetProjectionsByDateResponse> process(GetProjectionsByDateRequest operationInput);
}
