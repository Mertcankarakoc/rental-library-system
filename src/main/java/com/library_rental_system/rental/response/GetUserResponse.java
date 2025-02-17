package com.library_rental_system.rental.response;

import com.library_rental_system.rental.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse extends BaseResponse{
    private UserDto user;
}
