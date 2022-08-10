package com.project.cinema.rest.controller;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.model.request.GetProjectionsByGenreRequest;
import com.project.cinema.api.model.response.GetProjectionsByGenreResponse;
import com.project.cinema.api.operation.GetProjectionsByGenreProcessor;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectionController {
    private final GetProjectionsByGenreProcessor getProjectionsByGenreProcessor;

    public ProjectionController(GetProjectionsByGenreProcessor getProjectionsByGenreProcessor) {
        this.getProjectionsByGenreProcessor = getProjectionsByGenreProcessor;
    }

    @PostMapping("/movies/genre")
    public ResponseEntity<?> getMovieByGenre(@RequestBody GetProjectionsByGenreRequest genreRequest) {
        Either<Error, GetProjectionsByGenreResponse> result = getProjectionsByGenreProcessor.process(genreRequest);
        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
