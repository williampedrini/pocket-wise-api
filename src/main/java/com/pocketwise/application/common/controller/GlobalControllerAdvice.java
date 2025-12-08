package com.pocketwise.application.common.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("com.pocketwise.application")
class GlobalControllerAdvice {

    /**
     * Handles exceptions of type IllegalArgumentException and IllegalStateException
     * by returning their respective error messages and setting the HTTP status to BAD_REQUEST.
     *
     * @param exception the runtime exception to be handled
     * @return the message associated with the provided exception
     */
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    String handle(final RuntimeException exception) {
        return exception.getMessage();
    }
}
