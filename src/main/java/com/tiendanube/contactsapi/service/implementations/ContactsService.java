package com.tiendanube.contactsapi.service.implementations;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.tiendanube.contactsapi.dto.CreateContactRequest;
import com.tiendanube.contactsapi.dto.CreateContactResponse;
import com.tiendanube.contactsapi.dto.GetContactResponse;
import com.tiendanube.contactsapi.dto.UpdateContactRequest;
import com.tiendanube.contactsapi.error.exceptions.ContactAlreadyExistsException;
import com.tiendanube.contactsapi.error.exceptions.ContactNotFoundException;
import com.tiendanube.contactsapi.model.Contact;
import com.tiendanube.contactsapi.repository.ContactsRepository;

@Service
public class ContactsService implements com.tiendanube.contactsapi.service.ContactsService {
    private final ContactsRepository contactsRepository;
    private final ModelMapper modelMapper;

    public ContactsService(ContactsRepository contactsRepository, ModelMapper modelMapper) {
        this.contactsRepository = contactsRepository;
        this.modelMapper = modelMapper;
    }

    public CreateContactResponse createContact(CreateContactRequest createContactRequest) {
        Contact contact = modelMapper.map(createContactRequest, Contact.class);
        if (contactsRepository.existsByEmail(contact.getEmail())) {
            throw new ContactAlreadyExistsException("Email " + contact.getEmail() + " already exists");
        }

        contact.setCreatedAt(LocalDateTime.now());
        contact = contactsRepository.save(contact);

        return modelMapper.map(contact, CreateContactResponse.class);
    }

    public GetContactResponse getContact(String id) {
        Contact contact = contactsRepository.findById(id)
            .orElseThrow(() -> new ContactNotFoundException("Contact with id " + id + " not found"));
        return modelMapper.map(contact, GetContactResponse.class);
    }

    public void deleteContact(String id) {
        if (!contactsRepository.existsById(id)) {
            throw new ContactNotFoundException("Contact with id " + id + " not found");
        }
        contactsRepository.deleteById(id);
    }

    public GetContactResponse updateContact(String id, UpdateContactRequest updateContactRequest) {
        Contact contact = contactsRepository.findById(id)
            .orElseThrow(() -> new ContactNotFoundException("Contact with id " + id + " not found"));

        if (updateContactRequest.getEmail() != null) {
            contact.setEmail(updateContactRequest.getEmail());
        }

        if (updateContactRequest.getFirstName() != null) {
            contact.setFirstName(updateContactRequest.getFirstName());
        }

        if (updateContactRequest.getLastName() != null) {
            contact.setLastName(updateContactRequest.getLastName());
        }

        Contact savedContact = contactsRepository.save(contact);

        return modelMapper.map(savedContact, GetContactResponse.class);
    }
}
