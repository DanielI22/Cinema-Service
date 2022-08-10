package com.project.cinema.api.error;

import com.project.cinema.api.base.Error;
import org.springframework.http.HttpStatus;

public class GenreNotFoundError implements Error {
    @Override
    public String getMessage() {
        return "Genre is not found!";
    }

    @Override
    public HttpStatus getCode() {
        return HttpStatus.NOT_FOUND;
    }
}
