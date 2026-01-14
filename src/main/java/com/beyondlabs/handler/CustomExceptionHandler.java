package com.beyondlabs.handler;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler({ValidationException.class })
    public ResponseEntity<?> handle(RuntimeException ex) {
        return ResponseEntity.status(403).body(error("FORBIDDEN", ex.getMessage()));
    }
    
    private Map<String, Object> error(String code, String msg) {
        return Map.of(
                "error", code,
                "message", msg,
                "timestamp", Instant.now()
        );
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult()
                             .getFieldError()
                             .getDefaultMessage();
        
        return ResponseEntity.badRequest().body(
                Map.of(
                        "error", "VALIDATION_ERROR",
                        "message", msg,
                        "timestamp", Instant.now()
                )
        );
    }
}



