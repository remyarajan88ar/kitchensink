package org.quickstart.kitchensink.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> memberNotFoundExceptionHandler(MemberNotFoundException e) {
        log.error("Member Error: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Map<String, String> response = new HashMap<>();
        e.getConstraintViolations().forEach(error -> {
            response.put(error.getPropertyPath().toString(), error.getMessage());
        });
        log.error("Constraint Violation Error: {}", response);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> validationExceptionHandler(ValidationException e) {
        Map<String, String> response = new HashMap<>();
        response.put("email", "Email taken");
        log.error("Validation Error {}", response);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put("Error", e.getMessage());
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
