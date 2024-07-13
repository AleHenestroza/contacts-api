package com.tiendanube.contactsapi.error.exceptions;

public class ContactAlreadyExistsException extends RuntimeException {
    public ContactAlreadyExistsException(String message) {
        super(message);
    }
}
