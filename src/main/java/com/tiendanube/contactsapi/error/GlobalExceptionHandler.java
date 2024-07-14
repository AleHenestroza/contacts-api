package com.tiendanube.contactsapi.error;

import org.springframework.http.ResponseEntity;
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
}
