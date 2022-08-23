package com.project.cinema.data.crud.interfaces;


import com.project.cinema.data.crud.model.response.ProjectionReadResponse;

public interface ProjectionReadService {
    ProjectionReadResponse getProjection(Long id);
}
