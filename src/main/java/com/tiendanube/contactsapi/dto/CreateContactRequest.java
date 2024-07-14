package com.tiendanube.contactsapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactRequest {
    @Email
    @NotBlank
    private String email;
    private String firstName;
    private String lastName;
}
