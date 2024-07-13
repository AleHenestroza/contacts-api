package com.tiendanube.contactsapi.unit.service;

import com.tiendanube.contactsapi.dto.ContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.model.Contact;
import com.tiendanube.contactsapi.repository.ContactsRepository;
import com.tiendanube.contactsapi.service.implementations.ContactsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ContactsServiceTest {
    private static ContactsRepository contactsRepository;

    private static ModelMapper modelMapper;

    private static ContactsService contactsService;

    @BeforeAll
    static void setUp() {
        contactsRepository = Mockito.mock(ContactsRepository.class);
        modelMapper = new ModelMapper();
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

        ContactResponse contactResponse = contactsService.createContact(createContactRequest);

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

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            contactsService.createContact(createContactRequest);
        });
    }
}
