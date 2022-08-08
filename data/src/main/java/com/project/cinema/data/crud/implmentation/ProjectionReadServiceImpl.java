package com.project.cinema.data.crud.implmentation;

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
        Optional<ProjectionEntity> projection =
                Optional.ofNullable(projectionRepository.findById(id).orElseThrow());

        return ProjectionResponse.builder()
                .title(projection.get().getTitle())
                .description(projection.get().getDescription())
                .genre(projection.get().getGenre().getGenreName())
                .releaseDate(projection.get().getReleaseDate())
                .rating(projection.get().getRating())
                .projectionDate(projection.get().getProjectionDate())
                .projectionTime(projection.get().getProjectionTime())
                .ticketPrice(projection.get().getTicketPrice())
                .capacity(projection.get().getCapacity())
                .build();
    }
}
