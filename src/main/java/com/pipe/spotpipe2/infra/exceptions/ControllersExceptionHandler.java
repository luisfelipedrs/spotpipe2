package com.pipe.spotpipe2.infra.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
public class ControllersExceptionHandler {

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException e,
                                                              HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(status.value(), request.getRequestURI());
        response.addError(e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler({ ResourceAlreadyExistsException.class })
    public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceAlreadyExistsException e,
                                                              HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(status.value(), request.getRequestURI());
        response.addError(e.getMessage());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                             HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(status.value(), request.getRequestURI());
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(response::addError);
        return ResponseEntity.status(status).body(response);
    }
}
