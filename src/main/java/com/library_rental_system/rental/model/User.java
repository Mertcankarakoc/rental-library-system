package com.library_rental_system.rental.model;

import com.library_rental_system.rental.model.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 50)
    private String name;
    @Size(min = 3, max = 50)
    private String surname;
    @Email
    @NotNull(message = "Email is required")
    private String email;
    @Size(min = 6)
    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}
