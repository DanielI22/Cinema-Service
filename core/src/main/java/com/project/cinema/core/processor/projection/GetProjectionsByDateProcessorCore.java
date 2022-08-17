package com.project.cinema.core.processor.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.projection.ProjectionsNotFoundError;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.model.request.projection.GetProjectionsByDateRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByDateResponse;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.api.operation.projection.GetProjectionsByDateProcessor;
import com.project.cinema.core.exception.ProjectionNotFoundException;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.repository.ProjectionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetProjectionsByDateProcessorCore implements GetProjectionsByDateProcessor {
    private final ProjectionRepository projectionRepository;
    private final ConversionService conversionService;

    public GetProjectionsByDateProcessorCore(ProjectionRepository projectionRepository, ConversionService conversionService) {
        this.projectionRepository = projectionRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, GetProjectionsByDateResponse> process(GetProjectionsByDateRequest dateRequest) {
        return Try.of(() -> {
                    final List<ProjectionEntity> projections = projectionRepository
                            .findAllByProjectionDateBetween(dateRequest.getStartProjectionDate(), dateRequest.getEndProjectionDate());
                    if(projections.isEmpty()) {
                        throw new ProjectionNotFoundException();
                    }
                    return GetProjectionsByDateResponse
                            .builder()
                            .startProjectionDate(String.valueOf(dateRequest.getStartProjectionDate()))
                            .endProjectionDate(String.valueOf(dateRequest.getEndProjectionDate()))
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
