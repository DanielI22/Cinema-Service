package com.project.cinema.api.error.ticket;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class NoCapacityError implements Error {
    @Override
    public String getMessage() {
        return "No available seats for this projection!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.FORBIDDEN;
    }
}
