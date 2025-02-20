package com.library_rental_system.rental.controller;

import com.library_rental_system.rental.request.ChangePasswordRequest;
import com.library_rental_system.rental.request.UpdateProfileRequest;
import com.library_rental_system.rental.response.ChangePasswordResponse;
import com.library_rental_system.rental.response.GetUserResponse;
import com.library_rental_system.rental.response.GetUsersResponse;
import com.library_rental_system.rental.response.UpdateUserProfileResponse;
import com.library_rental_system.rental.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public GetUserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public GetUsersResponse getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/updateProfile")
    public UpdateUserProfileResponse updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return userService.updateProfile(updateProfileRequest, userDetails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping("/changePassword")
    public ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }
}
