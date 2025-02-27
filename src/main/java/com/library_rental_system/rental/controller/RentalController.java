package com.library_rental_system.rental.controller;

import com.library_rental_system.rental.response.RentalBookResponse;
import com.library_rental_system.rental.service.RentalService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rental")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public RentalBookResponse rentBook(@AuthenticationPrincipal UserDetails userDetails, String bookName) {
        return rentalService.rentalBook(userDetails, bookName);
    }
}
