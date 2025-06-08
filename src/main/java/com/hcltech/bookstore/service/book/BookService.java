package com.hcltech.bookstore.service.book;

import com.hcltech.bookstore.dto.BookDTO.BookRequestDTO;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO bookRequestDTO);

    List<BookResponseDTO> getAllBooks();

    BookResponseDTO getBookById(Long id);

    BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO);

    void deleteBook(Long id);

    BookResponseDTO assignBookToAuthor(Long bookId, Long authorId);

    void addStock(Long id, int quantity);
}
