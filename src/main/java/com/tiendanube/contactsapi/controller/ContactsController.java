package com.tiendanube.contactsapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.dto.CreateContactResponse;
import com.tiendanube.contactsapi.dto.GetContactResponse;
import com.tiendanube.contactsapi.dto.UpdateContactRequest;
import com.tiendanube.contactsapi.service.ContactsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @PostMapping()
    public ResponseEntity<CreateContactResponse> createContact(
        @RequestBody @Valid CreateContactRequest createContactRequest) {
        return ResponseEntity.status(201).body(contactsService.createContact(createContactRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetContactResponse> getContact(@PathVariable String id) {
        return ResponseEntity.ok(contactsService.getContact(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactsService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetContactResponse> updateContact(@PathVariable String id, @RequestBody @Valid UpdateContactRequest updateContactRequest) {
        GetContactResponse response = contactsService.updateContact(id, updateContactRequest);
        return ResponseEntity.ok(response);
    }
}
