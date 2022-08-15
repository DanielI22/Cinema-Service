package com.project.cinema.rest.controller;

import com.project.cinema.api.base.Error;
import com.project.cinema.api.model.request.projection.*;
import com.project.cinema.api.model.response.projection.*;
import com.project.cinema.api.operation.projection.*;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProjectionController {
    private final GetProjectionsByGenreProcessor getProjectionsByGenreProcessor;
    private final GetProjectionsSortedByRatingProcessor getProjectionsSortedByRatingProcessor;
    private final GetProjectionsByTitleProcessor getProjectionsByTitleProcessor;
    private final GetProjectionsByDateProcessor getProjectionsByDateProcessor;
    private final GetAvailableCapacityForProjectionProcessor getAvailableCapacityForProjectionProcessor;

    public ProjectionController(GetProjectionsByGenreProcessor getProjectionsByGenreProcessor, GetProjectionsSortedByRatingProcessor getProjectionsSortedByRatingProcessor, GetProjectionsByTitleProcessor getProjectionsByTitleProcessor, GetProjectionsByDateProcessor getProjectionsByDateProcessor, GetAvailableCapacityForProjectionProcessor getAvailableCapacityForProjectionProcessor) {
        this.getProjectionsByGenreProcessor = getProjectionsByGenreProcessor;
        this.getProjectionsSortedByRatingProcessor = getProjectionsSortedByRatingProcessor;
        this.getProjectionsByTitleProcessor = getProjectionsByTitleProcessor;
        this.getProjectionsByDateProcessor = getProjectionsByDateProcessor;
        this.getAvailableCapacityForProjectionProcessor = getAvailableCapacityForProjectionProcessor;
    }

    @GetMapping("/projections/all")
    public ResponseEntity<?> getProjectionsSortedRating() {
        Either<Error, GetProjectionsSortedByRatingResponse> result = getProjectionsSortedByRatingProcessor
                .process(new EmptyRequest());

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @GetMapping("/projections/genre")
    public ResponseEntity<?> getProjectionsByGenre(@RequestParam String genreName) {
        Either<Error, GetProjectionsByGenreResponse> result = getProjectionsByGenreProcessor
                .process(GetProjectionsByGenreRequest.builder().genreName(genreName).build());

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @GetMapping("/projections/title")
    public ResponseEntity<?> getProjectionsByTitle(@RequestParam String titleName) {
        Either<Error, GetProjectionsByTitleResponse> result = getProjectionsByTitleProcessor
                .process(GetProjectionsByTitleRequest.builder().title(titleName).build());

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }


    @PostMapping("/projections/date")
    public ResponseEntity<?> getProjectionsByDate(@Valid @RequestBody GetProjectionsByDateRequest getProjectionsByDateRequest) {
        Either<Error, GetProjectionsByDateResponse> result = getProjectionsByDateProcessor
                .process(getProjectionsByDateRequest);

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }

    @GetMapping("/projections/capacity")
    public ResponseEntity<?> getProjectionCapacity(@RequestParam Long projectionId) {
        Either<Error, GetAvailableCapacityForProjectionResponse> result = getAvailableCapacityForProjectionProcessor
                .process(GetAvailableCapacityForProjectionRequest.builder().projectionId(projectionId).build());

        if(result.isLeft()) {
            return ResponseEntity.status(result.getLeft().getCode()).body(result.getLeft().getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
