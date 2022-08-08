package com.project.cinema.data.crud.interfaces;


import com.project.cinema.data.crud.model.request.ProjectionPutRequest;
import com.project.cinema.data.crud.model.response.ProjectionResponse;

public interface ProjectionPutService {
    ProjectionResponse updateProjection(Long id, ProjectionPutRequest placePutRequest);
}
