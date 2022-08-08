package com.project.cinema.data.crud.interfaces;


import com.project.cinema.data.crud.model.response.ProjectionResponse;

public interface ProjectionReadService {
    ProjectionResponse getProjection(Long id);
}
