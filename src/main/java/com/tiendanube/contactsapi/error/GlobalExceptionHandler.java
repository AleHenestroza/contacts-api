package com.tiendanube.contactsapi.error;

import com.tiendanube.contactsapi.dto.ErrorResponse;
import com.tiendanube.contactsapi.error.exceptions.ContactAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleContactAlreadyExistsException(ContactAlreadyExistsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
