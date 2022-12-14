package com.project.cinema.api.operation.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.base.OperationProcessor;
import com.project.cinema.api.model.request.projection.GetProjectionsByGenreRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByGenreResponse;
import io.vavr.control.Either;

public interface GetProjectionsByGenreProcessor extends OperationProcessor<GetProjectionsByGenreRequest, GetProjectionsByGenreResponse> {
    @Override
    Either<Error, GetProjectionsByGenreResponse> process(GetProjectionsByGenreRequest genreRequest);
}
