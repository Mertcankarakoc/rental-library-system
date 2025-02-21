package com.library_rental_system.rental.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookRequest {

    private String name;
    private String title;
    private String author;
    private String isbn;
    private int availableCopies;
    private String categoryName;
}
