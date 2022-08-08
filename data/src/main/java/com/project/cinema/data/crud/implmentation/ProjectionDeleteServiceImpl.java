package com.project.cinema.data.crud.implmentation;

import com.project.cinema.data.crud.interfaces.ProjectionDeleteService;
import com.project.cinema.data.repository.ProjectionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectionDeleteServiceImpl implements ProjectionDeleteService {
    private final ProjectionRepository projectionRepository;

    public ProjectionDeleteServiceImpl(ProjectionRepository projectionRepository) {
        this.projectionRepository = projectionRepository;
    }

    @Override
    public Long deleteProjection(Long id) {
        projectionRepository.deleteById(id);
        return id;
    }
}
