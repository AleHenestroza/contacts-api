package com.tiendanube.contactsapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contacts")
public class Contact {
    @Id
    String id;

    @Indexed(unique = true)
    String email;

    String firstName;

    String lastName;

    @CreatedDate
    LocalDateTime createdAt;
}
