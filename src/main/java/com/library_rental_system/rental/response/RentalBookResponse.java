package com.library_rental_system.rental.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalBookResponse extends BaseResponse{
    private String bookName;
    private String rentalDate;
}
