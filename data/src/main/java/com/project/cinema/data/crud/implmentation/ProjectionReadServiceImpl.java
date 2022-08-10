package com.project.cinema.data.crud.implmentation;

import com.project.cinema.data.crud.exception.MovieNotFoundException;
import com.project.cinema.data.crud.interfaces.ProjectionReadService;
import com.project.cinema.data.crud.model.response.ProjectionResponse;
import com.project.cinema.data.entity.ProjectionEntity;
import com.project.cinema.data.repository.ProjectionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectionReadServiceImpl implements ProjectionReadService {
    private final ProjectionRepository projectionRepository;

    public ProjectionReadServiceImpl(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;
    }

    @Override
    public ProjectionResponse getProjection(Long id) {
        ProjectionEntity projection = projectionRepository.findById(id).orElseThrow(MovieNotFoundException::new);

        return ProjectionResponse.builder()
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
