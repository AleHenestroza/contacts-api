package com.tiendanube.contactsapi.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateContactRequest {
    @Email
    private String email;
    private String firstName;
    private String lastName;
}
