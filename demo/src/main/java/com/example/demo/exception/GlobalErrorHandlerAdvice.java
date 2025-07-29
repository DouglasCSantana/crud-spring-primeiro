package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandlerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(NotFoundException ex){
       var errors = new DefaultErrorMessage(HttpStatus.NOT_FOUND.value(),ex.getReason());
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
