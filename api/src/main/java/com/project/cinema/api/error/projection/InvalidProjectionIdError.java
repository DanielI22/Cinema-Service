package com.project.cinema.api.error.projection;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class InvalidProjectionIdError implements Error {
    @Override
    public String getMessage() {
        return "The entered projections Id is invalid!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
