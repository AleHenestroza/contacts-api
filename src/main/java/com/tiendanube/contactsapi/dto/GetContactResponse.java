package com.tiendanube.contactsapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetContactResponse {
    String id;
    String email;
    String firstName;
    String lastName;
    LocalDateTime createdAt;
}
