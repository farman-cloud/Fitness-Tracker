package com.fitnesstracker.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map; // Import Map

@ControllerAdvice // Makes this class intercept exceptions globally
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class) // Catch only this specific exception
    public ResponseEntity<Object> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {

        // Create a structured JSON error response
        Map<String, Object> body = Map.of(
                "timestamp", System.currentTimeMillis(),
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", ex.getMessage(),
                "path", request.getDescription(false).substring(4) // Get request URI
        );

        return new ResponseEntity<>(body, HttpStatus.CONFLICT); // Return 409 Conflict
    }

    // You can add more @ExceptionHandler methods here later for other exceptions
    // like ResourceNotFoundException, UnauthorizedException, ValidationException etc.
}