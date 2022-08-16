package com.project.cinema.api.error.ticket;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class TicketsNotFoundError implements Error {
    @Override
    public String getMessage() {
        return "No tickets were found for this user!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}
