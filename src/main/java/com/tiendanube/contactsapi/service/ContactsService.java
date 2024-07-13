package com.tiendanube.contactsapi.service;

import com.tiendanube.contactsapi.dto.ContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;

public interface ContactsService {
    ContactResponse createContact(CreateContactRequest createContactRequest);
}
