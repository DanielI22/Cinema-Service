package com.project.cinema.api.operation.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.projection.GetAvailableCapacityForProjectionRequest;
import com.project.cinema.api.model.response.projection.GetAvailableCapacityForProjectionResponse;
import io.vavr.control.Either;

public interface GetAvailableCapacityForProjectionProcessor extends OperationProcessor<GetAvailableCapacityForProjectionRequest, GetAvailableCapacityForProjectionResponse> {
    @Override
    Either<Error, GetAvailableCapacityForProjectionResponse> process(GetAvailableCapacityForProjectionRequest operationInput);
}
