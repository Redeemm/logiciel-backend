package com.reddev.logicielbackend.exception.HttpHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CREATED)
public class CreatedException extends Exception {

    private static final long serialVersionUID = 1L;

    public CreatedException(String message) {
        super(message);
    }
}
