package com.library_rental_system.rental.service;

import com.library_rental_system.rental.model.Book;
import com.library_rental_system.rental.model.Rental;
import com.library_rental_system.rental.model.User;
import com.library_rental_system.rental.repository.BookRepository;
import com.library_rental_system.rental.repository.RentalRepository;
import com.library_rental_system.rental.repository.UserRepository;
import com.library_rental_system.rental.response.RentalBookResponse;
import com.library_rental_system.rental.response.UpdateUserProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    public RentalBookResponse rentBook(Long userId, Long bookId) {
        RentalBookResponse response = new RentalBookResponse();

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            response.setMessage("Book not found");
            response.setStatus("NOT FOUND");
            response.setStatusCode(404);
            return response;
        }

        Book book = optionalBook.get();
        if (book.getAvailableCopies() <= 0) {
            response.setMessage("Book not available");
            response.setStatus("FAILURE");
            response.setStatusCode(409);
            return response;
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            response.setMessage("User not found");
            response.setStatus("NOT FOUND");
            response.setStatusCode(404);
            return response;
        }

        User user = optionalUser.get();

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(Date.valueOf(LocalDate.now()));
        rental.setReturnDate(Date.valueOf(LocalDate.now().plusDays(14)));
        rentalRepository.save(rental);

        response.setMessage("Book rented successfully");
        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        return response;

    }

    public RentalBookResponse rentalBook(UserDetails userDetails, String bookName) {
        RentalBookResponse response = new RentalBookResponse();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email);
        if (user == null) {
            response.setMessage("User not found");
            response.setStatus("NOT FOUND");
            response.setStatusCode(404);
            return response;
        }

        Optional<Book> optionalBook = bookRepository.findByName(bookName);
        if (optionalBook.isEmpty()) {
            response.setMessage("Book not found");
            response.setStatus("NOT FOUND");
            response.setStatusCode(404);
            return response;
        }

        Book book = optionalBook.get();
        if (book.getAvailableCopies() <= 0) {
            response.setMessage("Book not available");
            response.setStatus("FAILURE");
            response.setStatusCode(409);
            return response;
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(Date.valueOf(LocalDate.now()));
        rental.setReturnDate(Date.valueOf(LocalDate.now().plusDays(14)));
        rentalRepository.save(rental);

        response.setBookName(bookName);
        response.setRentalDate(LocalDate.now().toString());
        response.setMessage("Book rented successfully");
        response.setStatus("SUCCESS");
        response.setStatusCode(200);
        return response;

    }
}
