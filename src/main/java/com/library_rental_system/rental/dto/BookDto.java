package com.library_rental_system.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String bookName;
    private String title;
    private String author;
    private String isbn;
    private int totalBooks;
    private String category;
}
