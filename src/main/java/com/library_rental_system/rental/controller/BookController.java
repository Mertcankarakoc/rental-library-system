package com.library_rental_system.rental.controller;

import com.library_rental_system.rental.dto.CategoryDto;
import com.library_rental_system.rental.model.Category;
import com.library_rental_system.rental.request.CreateBookRequest;
import com.library_rental_system.rental.response.CreateBookResponse;
import com.library_rental_system.rental.response.GetBooksResponse;
import com.library_rental_system.rental.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all-books")
    public GetBooksResponse getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/create")
    public CreateBookResponse createBook(@RequestBody CreateBookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @DeleteMapping("/delete/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
    }

}
