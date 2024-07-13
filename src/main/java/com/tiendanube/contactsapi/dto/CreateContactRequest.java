package com.tiendanube.contactsapi.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactRequest {
    @Email
    String email;
    String firstName;
    String lastName;
}
