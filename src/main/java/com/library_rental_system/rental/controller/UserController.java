package com.library_rental_system.rental.controller;

import com.library_rental_system.rental.response.GetUserResponse;
import com.library_rental_system.rental.response.GetUsersResponse;
import com.library_rental_system.rental.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public GetUserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public GetUsersResponse getAllUsers() {
        return userService.getAllUsers();
    }

}
