package com.tiendanube.contactsapi.error;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.tiendanube.contactsapi.dto.ErrorResponse;
import com.tiendanube.contactsapi.error.exceptions.ContactAlreadyExistsException;
import com.tiendanube.contactsapi.error.exceptions.ContactNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleContactAlreadyExistsException(ContactAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleContactNotFoundException(ContactNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(404).body(errorResponse);
    }

    // Catch validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        if (e.getFieldError() == null) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
            return ResponseEntity.status(400).body(errorResponse);
        }

        FieldError fe = e.getFieldError();
        String message = String.format("%s: %s", fe.getField(), fe.getDefaultMessage());

        ErrorResponse errorResponse = new ErrorResponse(message, 400);
        return ResponseEntity.status(400).body(errorResponse);
    }
}
