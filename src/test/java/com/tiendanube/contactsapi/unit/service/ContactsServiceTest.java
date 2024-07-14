package com.tiendanube.contactsapi.unit.service;

import com.tiendanube.contactsapi.dto.CreateContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.dto.GetContactResponse;
import com.tiendanube.contactsapi.error.exceptions.ContactAlreadyExistsException;
import com.tiendanube.contactsapi.error.exceptions.ContactNotFoundException;
import com.tiendanube.contactsapi.model.Contact;
import com.tiendanube.contactsapi.repository.ContactsRepository;
import com.tiendanube.contactsapi.service.implementations.ContactsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ContactsServiceTest {
    private static ContactsRepository contactsRepository;

    private static ContactsService contactsService;

    @BeforeAll
    static void setUp() {
        contactsRepository = Mockito.mock(ContactsRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        contactsService = new ContactsService(contactsRepository, modelMapper);
    }

    @Test
    void test_createContact() {
        CreateContactRequest createContactRequest = new CreateContactRequest(
                "johndoe@email.com",
                "John",
                "Doe"
        );

        LocalDateTime createdAt = LocalDateTime.now();
        Contact savedContact = new Contact(
                "1",
                "johndow@email.com",
                "John",
                "Doe",
                createdAt
        );

        when(contactsRepository.save(any())).thenReturn(savedContact);

        CreateContactResponse contactResponse = contactsService.createContact(createContactRequest);

        Assertions.assertEquals("1", contactResponse.getId(), "The contact id is different from the expected");
        Assertions.assertEquals(createdAt, contactResponse.getCreatedAt(), "The contact createdAt is different from the expected");
    }

    @Test
    void test_createContact_emailAlreadyExists() {
        CreateContactRequest createContactRequest = new CreateContactRequest(
                "johndoe@email.com",
                "John",
                "Doe"
        );

        when(contactsRepository.existsByEmail("johndoe@email.com")).thenReturn(true);

        Assertions.assertThrows(ContactAlreadyExistsException.class, () -> {
            contactsService.createContact(createContactRequest);
        });
    }

    @Test
    void test_getContact() {
        String id = "1";

        LocalDateTime createdAt = LocalDateTime.now();
        Contact savedContact = new Contact(
                "1",
                "johndow@email.com",
                "John",
                "Doe",
                createdAt
        );

        when(contactsRepository.findById(id)).thenReturn(Optional.of(savedContact));

        GetContactResponse contact = contactsService.getContact(id);

        Assertions.assertEquals(savedContact.getId(), contact.getId(), "The contact id is different from the expected");
        Assertions.assertEquals(savedContact.getEmail(), contact.getEmail(), "The contact email is different from the expected");
        Assertions.assertEquals(savedContact.getFirstName(), contact.getFirstName(), "The contact firstName is different from the expected");
        Assertions.assertEquals(savedContact.getLastName(), contact.getLastName(), "The contact lastName is different from the expected");
        Assertions.assertEquals(savedContact.getCreatedAt(), contact.getCreatedAt(), "The contact createdAt is different from the expected");           
    }

    @Test
    void test_getContact_notFound() {
        String id = "1";

        when(contactsRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ContactNotFoundException.class, () -> {
            contactsService.getContact(id);
        });
    }

    @Test
    void test_deleteContact() {
        String id = "1";

        when(contactsRepository.existsById(id)).thenReturn(true);

        contactsService.deleteContact(id);

        Mockito.verify(contactsRepository).deleteById(id);
    }

    @Test
    void test_deleteContact_notFound() {
        String id = "1";

        when(contactsRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(ContactNotFoundException.class, () -> {
            contactsService.deleteContact(id);
        });
    }
}
