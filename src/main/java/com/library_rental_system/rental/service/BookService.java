package com.library_rental_system.rental.service;

import com.library_rental_system.rental.dto.BookDto;
import com.library_rental_system.rental.dto.CategoryDto;
import com.library_rental_system.rental.mapper.BookMapper;
import com.library_rental_system.rental.mapper.UserMapper;
import com.library_rental_system.rental.model.Book;
import com.library_rental_system.rental.model.Category;
import com.library_rental_system.rental.repository.BookRepository;
import com.library_rental_system.rental.repository.CategoryRepository;
import com.library_rental_system.rental.request.CreateBookRequest;
import com.library_rental_system.rental.response.CreateBookResponse;
import com.library_rental_system.rental.response.GetBooksResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.categoryRepository = categoryRepository;
    }

    public GetBooksResponse getAllBooks() {
        GetBooksResponse response = new GetBooksResponse();

        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            response.setMessage("No books found");
            response.setStatus("NOT FOUND");
            response.setStatusCode(404);
            return response;
        }

        List<BookDto> bookDtos = bookMapper.toBooksDto(books);
        response.setBooks(bookDtos);
        response.setMessage("Books found");
        response.setStatus("SUCCESS");
        response.setStatusCode(200);

        return response;
    }

    public CreateBookResponse createBook(CreateBookRequest createBookRequest) {
        CreateBookResponse response = new CreateBookResponse();

        if (bookRepository.existsByName(createBookRequest.getName())) {
            response.setMessage("Book already exists");
            response.setStatus("FAILURE");
            response.setStatusCode(409);
            return response;
        }
            Book book = new Book();
            book.setName(createBookRequest.getName());
            book.setTitle(createBookRequest.getTitle());
            book.setAuthor(createBookRequest.getAuthor());
            book.setIsbn(createBookRequest.getIsbn());
            book.setAvailableCopies(createBookRequest.getAvailableCopies());
            Category category = categoryRepository.findByName(createBookRequest.getCategoryName());
            if (category == null) {
                response.setMessage("Category not found");
                response.setStatus("NOT FOUND");
                response.setStatusCode(404);
                return response;
            }

        book.setCategory(category);

        bookRepository.save(book);

        response.setMessage("Book created successfully");
        response.setStatus("SUCCESS");
        response.setStatusCode(200);

        return response;
    }
}