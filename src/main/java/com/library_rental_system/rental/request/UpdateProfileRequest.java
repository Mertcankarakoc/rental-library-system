package com.library_rental_system.rental.request;

import com.library_rental_system.rental.dto.UpdateUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    private UpdateUserDto updateUserDto;
}
