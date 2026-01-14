package com.pocketwise.application.common.controller;

import static org.springframework.http.HttpStatus.*;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.access.AccessDeniedException;
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

    /**
     * Handles AccessDeniedException by returning the error message
     * and setting the HTTP status to FORBIDDEN (403).
     *
     * @param exception the access denied exception to be handled
     * @return the message associated with the provided exception
     */
    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    String handle(final AccessDeniedException exception) {
        return exception.getMessage();
    }

    /**
     * Handles EntityNotFoundException by returning the error message
     * and setting the HTTP status to NOT_FOUND (404).
     *
     * @param exception the entity not found exception to be handled
     * @return the message associated with the provided exception
     */
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    String handle(final EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
