package com.project.cinema.api.error.ticket;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class InvalidTicketTypeError implements Error {
    @Override
    public String getMessage() {
        return "Valid ticket types are: standard, VIP, children, student or pensioner";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
