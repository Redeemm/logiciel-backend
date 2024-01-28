package com.reddev.logicielbackend.exception;

public class ApiNotFoundException extends RuntimeException {

    public ApiNotFoundException(String message) {
        super(message);
    }

    public ApiNotFoundException(Throwable cause) {
        super(cause);
    }
}
