package com.project.cinema.core.processor.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.projection.ProjectionsNotFoundError;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.model.request.EmptyRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsSortedByRatingResponse;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.api.operation.projection.GetProjectionsSortedByRatingProcessor;
import com.project.cinema.core.exception.ProjectionNotFoundException;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.repository.ProjectionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProjectionsSortedByRatingProcessorCore implements GetProjectionsSortedByRatingProcessor {
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;

    public GetProjectionsSortedByRatingProcessorCore(ProjectionRepository projectionRepository, ConversionService conversionService) {
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, GetProjectionsSortedByRatingResponse> process(EmptyRequest operationInput) {
        return Try.of(() -> {
            final List<ProjectionEntity> projections = projectionRepository.findAll();
            if(projections.isEmpty()) {
                throw new ProjectionNotFoundException();
            }
            return GetProjectionsSortedByRatingResponse
                    .builder()
                    .projectionResponses(projections
                            .stream()
                            .sorted(Comparator.comparing(ProjectionEntity::getRating).reversed())
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

