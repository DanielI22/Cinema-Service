package com.project.cinema.data.crud.implmentation;

import com.project.cinema.data.crud.exception.MovieNotFoundException;
import com.project.cinema.data.crud.interfaces.ProjectionDeleteService;
import com.project.cinema.data.repository.projection.ProjectionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectionDeleteServiceImpl implements ProjectionDeleteService {
    private final ProjectionRepository projectionRepository;

    public ProjectionDeleteServiceImpl(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;
    }

    @Override
    public void deleteProjection(Long id) {
        if(!projectionRepository.existsById(id)) {
            throw new MovieNotFoundException();
        }
        projectionRepository.deleteById(id);
    }
}
