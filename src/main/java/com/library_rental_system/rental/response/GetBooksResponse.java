package com.library_rental_system.rental.response;

import com.library_rental_system.rental.dto.BookDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBooksResponse extends BaseResponse {
    private List<BookDto> books;
}
