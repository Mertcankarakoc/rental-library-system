package com.library_rental_system.rental.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String password;
}
