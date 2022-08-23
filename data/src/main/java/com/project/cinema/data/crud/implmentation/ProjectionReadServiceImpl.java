package com.project.cinema.data.crud.implmentation;

import com.project.cinema.data.crud.exception.MovieNotFoundException;
import com.project.cinema.data.crud.interfaces.ProjectionReadService;
import com.project.cinema.data.crud.model.response.ProjectionReadResponse;
import com.project.cinema.data.entity.projection.ProjectionEntity;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectionReadServiceImpl implements ProjectionReadService {
    private final ProjectionRepository projectionRepository;

    public ProjectionReadServiceImpl(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;
    }

    @Override
    public ProjectionReadResponse getProjection(Long id) {
        ProjectionEntity projection = projectionRepository.findById(id).orElseThrow(MovieNotFoundException::new);

        return ProjectionReadResponse.builder()
                .title(projection.getTitle())
                .description(projection.getDescription())
                .genre(projection.getGenre().getGenreName())
                .releaseDate(projection.getReleaseDate())
                .rating(projection.getRating())
                .projectionDate(projection.getProjectionDate())
                .projectionTime(projection.getProjectionTime())
                .ticketPrice(projection.getTicketPrice())
                .capacity(projection.getCapacity())
                .build();
    }
}
