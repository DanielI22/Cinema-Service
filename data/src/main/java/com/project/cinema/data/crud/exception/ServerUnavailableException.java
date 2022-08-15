package com.project.cinema.data.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.SERVICE_UNAVAILABLE, reason="No connection to Feign")
public class ServerUnavailableException extends RuntimeException{
}
