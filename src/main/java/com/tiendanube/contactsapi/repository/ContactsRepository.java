package com.tiendanube.contactsapi.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.tiendanube.contactsapi.model.Contact;

@Repository
public interface ContactsRepository extends MongoRepository<Contact, String> {
    boolean existsByEmail(String email);

    Optional<Contact> findByEmail(String email);
}
