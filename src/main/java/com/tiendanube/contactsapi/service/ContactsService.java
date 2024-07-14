package com.tiendanube.contactsapi.service;

import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.dto.CreateContactResponse;
import com.tiendanube.contactsapi.dto.GetContactResponse;
import com.tiendanube.contactsapi.dto.UpdateContactRequest;

public interface ContactsService {
    /***
     * Create a new contact
     * @param createContactRequest Request body
     * @return CreateContactResponse
     */
    CreateContactResponse createContact(CreateContactRequest createContactRequest);

    /***
     * Get a contact by id
     * @param id Contact id
     * @return GetContactResponse
     */
    GetContactResponse getContact(String id);

    /***
     * Delete a contact by id
     * @param id Contact id
     */
    void deleteContact(String id);

    /***
     * Update a contact by id
     * @param id Contact id
     * @param updateContactRequest Request body
     * @return GetContactResponse
     */
    GetContactResponse updateContact(String id, UpdateContactRequest updateContactRequest);
}
