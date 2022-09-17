package com.example.wizerassessment.services;

import com.example.wizerassessment.mapper.DTOS.BookDTO;
import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.model.Books;

import java.util.List;
import java.util.Optional;
import java.lang.Long;


public interface BookService {
    BookDTO createBook(BookDTO bookDTO) throws Exception;

    Optional<BookDTO> editBook(Books book, Long id);

    List<BookDTO> getBooks();

    CategoryDTO addBookTOCategory(Long bookId, String categoryName);

    void deleteBook(Long id);

    BookDTO getBook(Long id);
}
