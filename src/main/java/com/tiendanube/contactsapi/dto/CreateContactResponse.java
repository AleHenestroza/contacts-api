package com.tiendanube.contactsapi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CreateContactResponse {
    private String id;
    private LocalDateTime createdAt;
}
