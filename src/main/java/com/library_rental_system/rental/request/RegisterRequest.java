package com.library_rental_system.rental.request;

import com.library_rental_system.rental.model.Enum.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 15, message = "Name must be at least 3 characters")
    private String firstName;
    @NotNull(message = "Surname is required")
    @Size(min = 3, max = 20, message = "Surname must be at least 3 characters")
    private String lastName;
    @NotNull(message = "Email is required")
    @Email
    private String email;
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
