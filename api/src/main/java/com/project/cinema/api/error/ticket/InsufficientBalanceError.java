package com.project.cinema.api.error.ticket;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceError implements Error {
    @Override
    public String getMessage() {
        return "Insufficient card balance!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.PAYMENT_REQUIRED;
    }
}
