package com.project.cinema.api.operation.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.projection.GetProjectionsByTitleRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByTitleResponse;
import io.vavr.control.Either;

public interface GetProjectionsByTitleProcessor extends OperationProcessor<GetProjectionsByTitleRequest, GetProjectionsByTitleResponse> {
    @Override
    Either<Error, GetProjectionsByTitleResponse> process(GetProjectionsByTitleRequest operationInput);
}
