package com.library_rental_system.rental.repository;

import com.library_rental_system.rental.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByName(String name);

    Optional<Book> findByName(String name);
}
