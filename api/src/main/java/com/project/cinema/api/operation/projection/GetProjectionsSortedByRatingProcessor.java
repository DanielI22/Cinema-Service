package com.project.cinema.api.operation.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsSortedByRatingResponse;
import io.vavr.control.Either;

public interface GetProjectionsSortedByRatingProcessor extends OperationProcessor<EmptyRequest, GetProjectionsSortedByRatingResponse> {
    @Override
    Either<Error, GetProjectionsSortedByRatingResponse> process(EmptyRequest operationInput);
}
