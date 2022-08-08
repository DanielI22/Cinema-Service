package com.project.cinema.rest.controller;

import com.project.cinema.data.crud.interfaces.ProjectionCreateService;
import com.project.cinema.data.crud.interfaces.ProjectionDeleteService;
import com.project.cinema.data.crud.interfaces.ProjectionPutService;
import com.project.cinema.data.crud.interfaces.ProjectionReadService;
import com.project.cinema.data.crud.model.request.ProjectionCreateRequest;
import com.project.cinema.data.crud.model.request.ProjectionPutRequest;
import com.project.cinema.data.crud.model.response.ProjectionResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CrudController {
    private final ProjectionCreateService projectionCreateService;
    private final ProjectionReadService projectionReadService;
    private final ProjectionDeleteService projectionDeleteService;
    private final ProjectionPutService projectionPutService;

    public CrudController(ProjectionCreateService projectionCreateService, ProjectionReadService projectionReadService, ProjectionDeleteService projectionDeleteService, ProjectionPutService projectionPutService) {
        this.projectionCreateService = projectionCreateService;
        this.projectionReadService = projectionReadService;
        this.projectionDeleteService = projectionDeleteService;
        this.projectionPutService = projectionPutService;
    }


    @PostMapping("/projection/")
    public Long createProjection(@Valid @RequestBody ProjectionCreateRequest projectionCreateRequest) {
        return projectionCreateService.createProjection(projectionCreateRequest);
    }

    @GetMapping("/projection/")
    public ProjectionResponse getPlace(@RequestParam Long id) {
        return projectionReadService.getProjection(id);
    }

    @DeleteMapping("/projection/")
    public Long deletePlace(@RequestParam Long id) {
        projectionDeleteService.deleteProjection(id);
        return id;
    }

    @PutMapping("/projection/")
    public ProjectionResponse updatePlace(@RequestParam Long id, @RequestBody ProjectionPutRequest projectionPutRequest) {
        return projectionPutService.updateProjection(id, projectionPutRequest);
    }
}

