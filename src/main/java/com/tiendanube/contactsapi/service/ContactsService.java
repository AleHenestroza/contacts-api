package com.tiendanube.contactsapi.service;

import com.tiendanube.contactsapi.dto.CreateContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.dto.GetContactResponse;

public interface ContactsService {
    CreateContactResponse createContact(CreateContactRequest createContactRequest);
    GetContactResponse getContact(String id);
}
