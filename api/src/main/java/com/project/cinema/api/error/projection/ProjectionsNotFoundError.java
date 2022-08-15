package com.project.cinema.api.error.projection;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class ProjectionsNotFoundError implements Error {
    @Override
    public String getMessage() {
        return "No projections have met your criteria!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}
