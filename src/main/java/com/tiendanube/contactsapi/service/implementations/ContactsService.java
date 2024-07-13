package com.tiendanube.contactsapi.service.implementations;

import com.tiendanube.contactsapi.dto.ContactResponse;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.error.exceptions.ContactAlreadyExistsException;
import com.tiendanube.contactsapi.model.Contact;
import com.tiendanube.contactsapi.repository.ContactsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ContactsService implements com.tiendanube.contactsapi.service.ContactsService {
    private final ContactsRepository contactsRepository;
    private final ModelMapper modelMapper;

    public ContactsService(ContactsRepository contactsRepository, ModelMapper modelMapper) {
        this.contactsRepository = contactsRepository;
        this.modelMapper = modelMapper;
    }

    public ContactResponse createContact(CreateContactRequest createContactRequest) {
        Contact contact = modelMapper.map(createContactRequest, Contact.class);
        if (contactsRepository.existsByEmail(contact.getEmail())) {
            throw new ContactAlreadyExistsException("Email " + contact.getEmail() + " already exists");
        }

        contact = contactsRepository.save(contact);

        return modelMapper.map(contact, ContactResponse.class);
    }
}
