package com.tiendanube.contactsapi.controller;

import com.tiendanube.contactsapi.dto.ContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.service.ContactsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @PostMapping()
    public ResponseEntity<ContactResponse> createContact(@RequestBody @Valid CreateContactRequest createContactRequest) {
        return ResponseEntity.status(201).body(contactsService.createContact(createContactRequest));
    }
}
