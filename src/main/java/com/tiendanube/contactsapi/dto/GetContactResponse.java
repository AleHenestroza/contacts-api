package com.tiendanube.contactsapi.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class GetContactResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
}
