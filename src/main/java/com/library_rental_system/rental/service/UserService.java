package com.library_rental_system.rental.service;

import com.library_rental_system.rental.dto.AddressDto;
import com.library_rental_system.rental.dto.UpdateUserDto;
import com.library_rental_system.rental.dto.UserDto;
import com.library_rental_system.rental.mapper.UserMapper;
import com.library_rental_system.rental.model.Address;
import com.library_rental_system.rental.model.User;
import com.library_rental_system.rental.repository.AddressRepository;
import com.library_rental_system.rental.repository.UserRepository;
import com.library_rental_system.rental.request.ChangePasswordRequest;
import com.library_rental_system.rental.request.UpdateProfileRequest;
import com.library_rental_system.rental.response.ChangePasswordResponse;
import com.library_rental_system.rental.response.GetUserResponse;
import com.library_rental_system.rental.response.GetUsersResponse;
import com.library_rental_system.rental.response.UpdateUserProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public GetUsersResponse getAllUsers() {

        GetUsersResponse getUsersResponse = new GetUsersResponse();
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            getUsersResponse.setMessage("No users found");
            getUsersResponse.setStatus("NOT FOUND");
            getUsersResponse.setStatusCode(404);
            return getUsersResponse;
        }

        List<UserDto> userDtos = userMapper.toUsersDto(users);
        getUsersResponse.setUsers(userDtos);
        getUsersResponse.setMessage("Users found");
        getUsersResponse.setStatus("SUCCESS");
        getUsersResponse.setStatusCode(200);
        return getUsersResponse;
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

    public UpdateUserProfileResponse updateProfile(UpdateProfileRequest updateProfileRequest, UserDetails userDetails) {
        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return UpdateUserProfileResponse.userProfileResponse("User not found", "NOT FOUND", 404, null);
        }

        UpdateUserDto updateUserDto = updateProfileRequest.getUpdateUserDto();
        if (updateUserDto == null) {
            return UpdateUserProfileResponse.userProfileResponse("User details cannot be null", "BAD REQUEST", 400, null);
        }

        if (updateUserDto.getPhoneNumber() != null && !updateUserDto.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        }
        if (updateUserDto.getEmail() != null && !updateUserDto.getEmail().isEmpty()) {
            user.setEmail(updateUserDto.getEmail());
        }

        if (updateUserDto.getAddress() != null) {
            AddressDto addressDto = updateUserDto.getAddress();

            Address existingAddress = user.getAddress();
            boolean isSharedAddress = existingAddress != null && existingAddress.getUsers().size() > 1;

            Address address;
            if (isSharedAddress) {
                address = new Address();
                address.setStreet(addressDto.getStreet());
                address.setCity(addressDto.getCity());
                address.setCountry(addressDto.getCountry());
                address.setDescription(addressDto.getDescription());
                address.setZipCode(addressDto.getZipCode());
                addressRepository.save(address);
            } else {
                address = existingAddress != null ? existingAddress : new Address();
                address.setStreet(addressDto.getStreet());
                address.setCity(addressDto.getCity());
                address.setCountry(addressDto.getCountry());
                address.setDescription(addressDto.getDescription());
                address.setZipCode(addressDto.getZipCode());

                addressRepository.save(address);
            }

            user.setAddress(address);
        }

        userRepository.save(user);

        return UpdateUserProfileResponse.userProfileResponse("User updated successfully", "SUCCESS", 200, user.getId());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        ChangePasswordResponse response = new ChangePasswordResponse();

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(changePasswordRequest.getEmail()));
        if (optionalUser.isEmpty()) {
            response.setStatus("NOT FOUND");
            response.setMessage("User not found");
            response.setStatusCode(404);
            return response;
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),user.getPassword())) {
            response.setMessage("Old password is incorrect");
            response.setStatusCode(400);
            return response;
        }

        String hashedNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(hashedNewPassword);
        userRepository.save(user);

        response.setStatus("SUCCESS");
        response.setMessage("Password changed successfully");
        response.setStatusCode(200);

        return response;
    }



}
