package com.tiendanube.contactsapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateContactResponse {
    String id;
    LocalDateTime createdAt;
}
