package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.dto.BookDTO.BookRequestDTO;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;
import com.hcltech.bookstore.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO bookRequestDTO) {
        return new ResponseEntity<>(bookService.createBook(bookRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,
                                                      @Valid @RequestBody BookRequestDTO bookRequestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/books/{id}/stock/add")
    public ResponseEntity<String> addStock(@PathVariable Long id, @RequestParam int quantity) {
        bookService.addStock(id, quantity);
        return ResponseEntity.ok("Stock updated successfully");
    }


    @PostMapping("/{bookId}/assign-author/{authorId}")
    public ResponseEntity<BookResponseDTO> assignBookToAuthor(@PathVariable Long bookId,
                                                              @PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.assignBookToAuthor(bookId, authorId));
    }
}

