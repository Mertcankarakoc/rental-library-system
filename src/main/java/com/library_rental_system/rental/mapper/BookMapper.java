package com.library_rental_system.rental.mapper;

import com.library_rental_system.rental.dto.BookDto;
import com.library_rental_system.rental.dto.CategoryDto;
import com.library_rental_system.rental.model.Book;
import com.library_rental_system.rental.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    List<BookDto> toBooksDto(List<Book> books);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "bookName")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "availableCopies", target = "totalBooks")
    @Mapping(source = "category.name", target = "category")
    BookDto toBookDto(Book book);


    /*@Mapping(source = "bookName", target = "name")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "totalBooks", target = "availableCopies")
    Book toBook(BookDto bookDto);*/

    Category toCategory(CategoryDto categoryDto);
}
