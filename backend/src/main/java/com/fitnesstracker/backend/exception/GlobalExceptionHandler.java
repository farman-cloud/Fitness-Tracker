package com.fitnesstracker.backend.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameConflictException.class)
    public ResponseEntity<Object> handleUsernameConflictException(
            UsernameConflictException ex, WebRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.CONFLICT, "Conflict", ex.getMessage(), request);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), request);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.FORBIDDEN, "Forbidden", ex.getMessage(), request);
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = createErrorBody(HttpStatus.BAD_REQUEST, "Validation Failed", errors, request);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createErrorBody(HttpStatus status, String error, Object message, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        try {
            body.put("path", request.getDescription(false).substring(4));
        } catch (Exception e) {
            body.put("path", "Unknown");
        }
        return body;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(
            Exception ex, WebRequest request) {
        logger.error("An unexpected error occurred: ", ex);

        Map<String, Object> body = createErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred. Please try again later.", request);
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}