package com.project.cinema.core.processor.projection;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.error.projection.GenreNotFoundError;
import com.project.cinema.api.error.ServiceUnavailableError;
import com.project.cinema.api.model.request.projection.GetProjectionsByGenreRequest;
import com.project.cinema.api.model.response.projection.GetProjectionsByGenreResponse;
import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.api.operation.projection.GetProjectionsByGenreProcessor;
import com.project.cinema.data.entity.Genre;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.repository.GenreRepository;
import com.project.cinema.data.repository.ProjectionRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GetProjectionsByGenreProcessorCore implements GetProjectionsByGenreProcessor {
    private final ProjectionRepository projectionRepository;
    private final GenreRepository genreRepository;
    private final ConversionService conversionService;

    public GetProjectionsByGenreProcessorCore(ProjectionRepository projectionRepository, GenreRepository genreRepository, ConversionService conversionService) {
        this.projectionRepository = projectionRepository;
        this.genreRepository = genreRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Either<Error, GetProjectionsByGenreResponse> process(GetProjectionsByGenreRequest genreRequest) {
        return Try.of(() -> {
            final Genre genre = genreRepository.findByGenreName(genreRequest.getGenreName()).orElseThrow();
            final List<ProjectionEntity> projections = projectionRepository.findAllByGenre(genre);
            return GetProjectionsByGenreResponse
                    .builder()
                    .genre(genre.getGenreName())
                    .projectionResponses(projections
                            .stream()
                            .map(pr -> conversionService.convert(pr, ProjectionResponse.class))
                            .collect(Collectors.toList()))
                    .build();

            }).toEither()
                .mapLeft(throwable -> {
                    if(throwable instanceof NoSuchElementException) {
                        return new GenreNotFoundError();
                    }
                    return new ServiceUnavailableError();
                });
    }
}
