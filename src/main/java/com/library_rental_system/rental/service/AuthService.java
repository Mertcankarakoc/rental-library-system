package com.library_rental_system.rental.service;

import com.library_rental_system.rental.model.Enum.Role;
import com.library_rental_system.rental.model.User;
import com.library_rental_system.rental.repository.UserRepository;
import com.library_rental_system.rental.request.LoginRequest;
import com.library_rental_system.rental.request.RegisterRequest;
import com.library_rental_system.rental.response.LoginResponse;
import com.library_rental_system.rental.response.RegisterResponse;
import com.library_rental_system.rental.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            registerResponse.setMessage("User already exists");
            registerResponse.setStatus("FAILURE");
            registerResponse.setStatusCode(409);
            return registerResponse;
        }

        try {

            User user = new User();
            user.setName(registerRequest.getName());
            user.setSurname(registerRequest.getSurname());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            if (registerRequest.getRole() == null) {
                user.setRole(Role.USER);
            } else {
                user.setRole(registerRequest.getRole());
            }

            userRepository.save(user);

            registerResponse.setMessage("User registered successfully.");
            registerResponse.setStatus("SUCCESS");
            registerResponse.setStatusCode(201);

        } catch (Exception exception) {
            registerResponse.setMessage("An error occurred: " + exception.getMessage());
            registerResponse.setStatus("FAILURE");
            registerResponse.setStatusCode(500);
        }
        return registerResponse;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        User user = this.userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            loginResponse.setMessage("User not found");
            loginResponse.setStatus("FAILURE");
            loginResponse.setStatusCode(400);
            return loginResponse;
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            logger.info("password didn't match.");
            loginResponse.setMessage("Email or password is incorrect");
            loginResponse.setStatus("UNAUTHORIZED");
            loginResponse.setStatusCode(401);
            return loginResponse;
        }

        try {
            String token = jwtUtil.createToken(user);
            loginResponse.setMessage("Login successful");
            loginResponse.setStatus("SUCCESS");
            loginResponse.setStatusCode(200);
            loginResponse.setToken(token);

        } catch (Exception ex) {
            loginResponse.setMessage("An error occurred while generating token: " + ex.getMessage());
            loginResponse.setStatus("FAILURE");
            loginResponse.setStatusCode(500);
        }

        return loginResponse;

    }
}