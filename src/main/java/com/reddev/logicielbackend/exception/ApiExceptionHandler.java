package com.reddev.logicielbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {ApiNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ApiRequestException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {ApiBadRequestFoundException.class})
    public ResponseEntity<Object> handleApiBadRequestException(ApiRequestException e) {
        HttpStatus bad = HttpStatus.NOT_ACCEPTABLE;
        ApiException apiException = new ApiException(
                e.getMessage(),
                bad,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, bad);
    }
}
