package com.library_rental_system.rental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name")
    private String name;
    @Column(name = "book_title")
    private String title;
    private String author;
    private String isbn;
    @Column(name = "total_books")
    private int availableCopies;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
