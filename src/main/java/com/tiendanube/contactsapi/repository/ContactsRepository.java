package com.tiendanube.contactsapi.repository;

import com.tiendanube.contactsapi.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactsRepository extends MongoRepository<Contact, String> {
    boolean existsByEmail(String email);
    Optional<Contact> findByEmail(String email);
}
