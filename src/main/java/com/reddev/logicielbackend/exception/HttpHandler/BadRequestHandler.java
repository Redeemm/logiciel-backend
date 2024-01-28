package com.reddev.logicielbackend.exception.HttpHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class BadRequestHandler extends RuntimeException {

}
