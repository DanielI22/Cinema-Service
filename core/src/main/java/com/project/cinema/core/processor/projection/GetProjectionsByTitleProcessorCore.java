package com.project.cinema.core.processor.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.projection.ProjectionsNotFoundError;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.model.request.projection.GetProjectionsByTitleRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByTitleResponse;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.api.operation.projection.GetProjectionsByTitleProcessor;
import com.project.cinema.core.exception.ProjectionNotFoundException;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProjectionsByTitleProcessorCore implements GetProjectionsByTitleProcessor {
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;

    public GetProjectionsByTitleProcessorCore(ProjectionRepository projectionRepository, ConversionService conversionService) {
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, GetProjectionsByTitleResponse> process(GetProjectionsByTitleRequest getProjectionsByTitleRequest) {
        return Try.of(() -> {
                    final List<ProjectionEntity> projections = projectionRepository
                            .findAllByTitle(getProjectionsByTitleRequest.getTitle());
                    if(projections.isEmpty()) {
                        throw new ProjectionNotFoundException();
                    }
                    return GetProjectionsByTitleResponse
                            .builder()
                            .title(getProjectionsByTitleRequest.getTitle())
                            .projectionResponses(projections
                                    .stream()
                                    .map(pr -> conversionService.convert(pr, ProjectionResponse.class))
                                    .collect(Collectors.toList()))
                            .build();

                }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof ProjectionNotFoundException) {
                        return new ProjectionsNotFoundError();
                    }
                    return new ServiceUnavailableError();
                });
    }
}
