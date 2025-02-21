package com.library_rental_system.rental.dto;

import com.library_rental_system.rental.model.Address;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private Address address;
    private String email;
    private String password;
}
