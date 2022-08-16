package com.project.cinema.api.error.ticket;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsError implements Error {
    @Override
    public String getMessage() {
        return "Invalid card credentials!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
