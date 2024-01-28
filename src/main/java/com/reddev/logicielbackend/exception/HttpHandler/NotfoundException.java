package com.reddev.logicielbackend.exception.HttpHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class NotfoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public NotfoundException(String message) {
        super(message);
    }
}
