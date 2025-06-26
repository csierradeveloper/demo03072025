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

    //TODO: fill in with more specific handling
//    @ExceptionHandler({
//            IllegalArgumentException.class,
//            IllegalStateException.class,
//            RuntimeException.class
//    })
//    ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
//        log.error("Exception: " + ex.toString());
//        log.error("Request: " + request.toString());
//        String bodyOfResponse = "Request failed, see logs";
//        return super.handleExceptionInternal(ex, bodyOfResponse,
//                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//    }
}
