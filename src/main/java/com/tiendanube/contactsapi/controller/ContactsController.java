package com.tiendanube.contactsapi.controller;

import com.tiendanube.contactsapi.dto.CreateContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.dto.GetContactResponse;
import com.tiendanube.contactsapi.service.ContactsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @PostMapping()
    public ResponseEntity<CreateContactResponse> createContact(@RequestBody @Valid CreateContactRequest createContactRequest) {
        return ResponseEntity.status(201).body(contactsService.createContact(createContactRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetContactResponse> getContact(@PathVariable String id) {
        return ResponseEntity.ok(contactsService.getContact(id));
    }
}
