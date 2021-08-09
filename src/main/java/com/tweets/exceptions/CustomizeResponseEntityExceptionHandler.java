package com.tweets.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
