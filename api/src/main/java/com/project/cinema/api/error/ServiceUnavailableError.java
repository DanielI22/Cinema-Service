package com.project.cinema.api.error;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class ServiceUnavailableError implements Error {
    @Override
    public String getMessage() {
        return "Unhandled Exception";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.SERVICE_UNAVAILABLE;
    }
}
