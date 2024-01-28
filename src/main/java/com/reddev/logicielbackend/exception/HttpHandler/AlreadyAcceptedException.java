package com.reddev.logicielbackend.exception.HttpHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class AlreadyAcceptedException extends Exception {

    private static final long serialVersionUID = 1L;

    public AlreadyAcceptedException(String message) {
        super(message);
    }
}
