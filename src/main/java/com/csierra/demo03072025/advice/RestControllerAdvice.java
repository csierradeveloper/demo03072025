package com.csierra.demo03072025.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
        log.error("Exception: " + ex.toString());
        log.error("Request: " + request.toString());
        String bodyOfResponse = "Request failed validation: " + ex.getMessage();
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({
            IllegalStateException.class
    })
    ResponseEntity<Object> handleIllegalState(RuntimeException ex, WebRequest request) {
        log.error("Exception: " + ex.toString());
        log.error("Request: " + request.toString());
        String bodyOfResponse = ex.getMessage();
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler({
            InternalError.class
    })
    ResponseEntity<Object> handleInternalError(RuntimeException ex, WebRequest request) {
        log.error("Exception: " + ex.toString());
        log.error("Request: " + request.toString());
        String bodyOfResponse = "Internal error: " + ex.getMessage();
        return super.handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    //TODO: additional handlers as necessary
}
