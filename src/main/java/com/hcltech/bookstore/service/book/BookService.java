package com.hcltech.bookstore.service.book;

import com.hcltech.bookstore.dto.book.BookRequestDto;
import com.hcltech.bookstore.dto.book.BookResponseDto;

import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto bookRequestDTO);

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookById(Long id);

    BookResponseDto updateBook(Long id, BookRequestDto bookRequestDTO);

    void deleteBook(Long id);

    BookResponseDto assignBookToAuthor(Long bookId, Long authorId);

    void addStock(Long id, int quantity);
}
