package com.library_rental_system.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {

    private Long id;
    private Long bookId;
    private Long userId;
    private String rentalDate;
    private String returnDate;
}
