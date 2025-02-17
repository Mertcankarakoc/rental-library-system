package com.library_rental_system.rental.service;

import com.library_rental_system.rental.dto.UserDto;
import com.library_rental_system.rental.mapper.UserMapper;
import com.library_rental_system.rental.model.User;
import com.library_rental_system.rental.repository.UserRepository;
import com.library_rental_system.rental.response.GetUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public GetUserResponse getUserById(Long userId) {
        GetUserResponse getUserResponse = new GetUserResponse();
        if (userId == null) {
            getUserResponse.setMessage("User id cannot be null");
            getUserResponse.setStatus("BAD REQUEST");
            getUserResponse.setStatusCode(400);
            return getUserResponse;
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            getUserResponse.setMessage("User not found");
            getUserResponse.setStatus("NOT FOUND");
            getUserResponse.setStatusCode(404);
            return getUserResponse;
        }

        User user = userOptional.get();
        UserDto userDto = userMapper.toUserDto(user);
        getUserResponse.setUser(userDto);
        getUserResponse.setMessage("User found");
        getUserResponse.setStatus("SUCCESS");
        getUserResponse.setStatusCode(200);
        return getUserResponse;
    }


}
