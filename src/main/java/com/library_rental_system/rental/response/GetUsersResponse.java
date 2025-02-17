package com.library_rental_system.rental.response;

import com.library_rental_system.rental.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUsersResponse extends BaseResponse{

    private List<UserDto> users;
}
