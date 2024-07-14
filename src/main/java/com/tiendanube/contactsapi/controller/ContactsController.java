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
import com.tiendanube.contactsapi.dto.ErrorResponse;
import com.tiendanube.contactsapi.dto.GetContactResponse;
import com.tiendanube.contactsapi.dto.UpdateContactRequest;
import com.tiendanube.contactsapi.service.ContactsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contacts")
public class ContactsController {
    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @Operation(summary = "Create a new contact")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Contact created successfully",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CreateContactResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request body",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping()
    public ResponseEntity<CreateContactResponse> createContact(
        @RequestBody @Valid CreateContactRequest createContactRequest
    ) {
        return ResponseEntity.status(201).body(contactsService.createContact(createContactRequest));
    }

    @Operation(summary = "Get a contact by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Contact found",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetContactResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Contact not found",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetContactResponse> getContact(@PathVariable String id) {
        return ResponseEntity.ok(contactsService.getContact(id));
    }

    @Operation(summary = "Delete a contact by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contact deleted"),
        @ApiResponse(
            responseCode = "404",
            description = "Contact not found",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id) {
        contactsService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a contact by id")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Contact updated",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = GetContactResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Contact not found",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request body",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetContactResponse> updateContact(
        @PathVariable String id,
        @RequestBody @Valid UpdateContactRequest updateContactRequest
    ) {
        GetContactResponse response = contactsService.updateContact(id, updateContactRequest);
        return ResponseEntity.ok(response);
    }
}
