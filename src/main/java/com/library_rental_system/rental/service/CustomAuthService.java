package com.library_rental_system.rental.service;

import com.library_rental_system.rental.model.User;
import com.library_rental_system.rental.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user.getEmail() == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().toString())
                .accountExpired(true)
                .accountLocked(true)
                .credentialsExpired(true)
                .disabled(true)
                .build();
        return userDetails;
    }

}
