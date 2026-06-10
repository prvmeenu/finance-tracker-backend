package com.example.FinanceTracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        ));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //Resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    //Forbidden
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbidden(ForbiddenException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    //Duplicate Resource
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicate(DuplicateResourceException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    //General Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        //ex.getClass().getName() + " : " + ex.getMessage()
    }
}
