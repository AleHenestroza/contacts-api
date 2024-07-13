package com.tiendanube.contactsapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactResponse {
    String id;
    LocalDateTime createdAt;
}
