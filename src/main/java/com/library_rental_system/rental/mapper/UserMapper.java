package com.library_rental_system.rental.mapper;

import com.library_rental_system.rental.dto.UserDto;
import com.library_rental_system.rental.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /*UserDto toDto(User user); // This method is used to convert a User entity to a UserDto
    User toEntity(UserDto userDto); // This method is used to convert a UserDto to a User entity*/

    @Mapping(source = "firstName", target = "name")
    @Mapping(source = "lastName", target = "surname")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "password", source = "password", ignore = true)
    UserDto toUserDto(User user);
}
