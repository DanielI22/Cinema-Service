package com.project.cinema.core.convertor;

import com.project.cinema.api.model.response.projection.ProjectionResponse;
import com.project.cinema.data.entity.ProjectionEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectionEntityToProjectionResponseConverter implements Converter<ProjectionEntity, ProjectionResponse> {
    @Override
    public ProjectionResponse convert(ProjectionEntity source) {
        return ProjectionResponse.builder()
                .projectionId(String.valueOf(source.getProjectionId()))
                .title(source.getTitle())
                .description(source.getDescription())
                .genre(source.getGenre().getGenreName())
                .releaseDate(source.getReleaseDate())
                .rating(String.valueOf(source.getRating()))
                .projectionDate(String.valueOf(source.getProjectionDate()))
                .projectionTime(String.valueOf(source.getProjectionTime()))
                .ticketPrice(String.valueOf(source.getTicketPrice()))
                .capacity(String.valueOf(source.getCapacity()))
                .build();
    }
}
