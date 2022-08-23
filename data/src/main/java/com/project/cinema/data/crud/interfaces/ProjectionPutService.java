package com.project.cinema.data.crud.interfaces;


import com.project.cinema.data.crud.model.request.ProjectionPutRequest;
import com.project.cinema.data.crud.model.response.ProjectionReadResponse;

public interface ProjectionPutService {
    ProjectionReadResponse updateProjection(Long id, ProjectionPutRequest placePutRequest);
}
