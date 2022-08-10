package com.project.cinema.data.crud.mapper;

import com.project.cinema.data.crud.model.response.ProjectionResponse;
import com.project.cinema.data.entity.ProjectionEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectionEntityToProjectionResponse {
    public ProjectionResponse map(ProjectionEntity projectionEntity) {
       return ProjectionResponse.builder()
                .title(projectionEntity.getTitle())
                .description(projectionEntity.getDescription())
                .genre(projectionEntity.getGenre().getGenreName())
                .releaseDate(projectionEntity.getReleaseDate())
                .rating(projectionEntity.getRating())
                .projectionDate(projectionEntity.getProjectionDate())
                .projectionTime(projectionEntity.getProjectionTime())
                .ticketPrice(projectionEntity.getTicketPrice())
                .capacity(projectionEntity.getCapacity())
                .build();
    }
}
