package com.project.cinema.core.processor.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.projection.InvalidProjectionIdError;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.model.request.projection.GetAvailableCapacityForProjectionRequest;
import com.project.cinema.api.model.response.projection.GetAvailableCapacityForProjectionResponse;
import com.project.cinema.api.operation.projection.GetAvailableCapacityForProjectionProcessor;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.repository.ProjectionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GetAvailableCapacityForProjectionProcessorCore implements GetAvailableCapacityForProjectionProcessor {
    private final ProjectionRepository projectionRepository;

    public GetAvailableCapacityForProjectionProcessorCore(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;
    }

    @Override
    public Either<Error, GetAvailableCapacityForProjectionResponse> process(GetAvailableCapacityForProjectionRequest projectionRequest) {
        return Try.of(() -> {
            final ProjectionEntity projection = projectionRepository.findById(projectionRequest.getProjectionId()).orElseThrow();
            return GetAvailableCapacityForProjectionResponse.builder()
                    .projectionId(String.valueOf(projection.getProjectionId()))
                    .projectionTitle(projection.getTitle())
                    .availableCapacity(String.valueOf(projection.getCapacity()))
                    .build();
        }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof NoSuchElementException) {
                        return new InvalidProjectionIdError();
                    }
                    return new ServiceUnavailableError();
                });
    }
}
