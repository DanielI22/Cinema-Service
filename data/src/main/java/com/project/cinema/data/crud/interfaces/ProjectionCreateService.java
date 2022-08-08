package com.project.cinema.data.crud.interfaces;


import com.project.cinema.data.crud.model.request.ProjectionCreateRequest;

public interface ProjectionCreateService {
    Long createProjection(ProjectionCreateRequest projectionCreateRequest);
}
