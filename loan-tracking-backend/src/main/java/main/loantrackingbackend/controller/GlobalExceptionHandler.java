package main.loantrackingbackend.controller;

import main.loantrackingbackend.exception.DuplicateResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResource(DuplicateResourceException exception) {

        Map<String, String> response = Collections.singletonMap("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
