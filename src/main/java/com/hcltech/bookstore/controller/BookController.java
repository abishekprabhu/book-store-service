package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.dto.book.BookRequestDto;
import com.hcltech.bookstore.dto.book.BookResponseDto;
import com.hcltech.bookstore.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDto>  createBook(@Valid BookRequestDto bookRequestDTO) {
        return new ResponseEntity<>(bookService.createBook(bookRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Long id) {
        BookResponseDto book = bookService.getBookById(id);
        if (book.getImageUrl() == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageBytes = java.util.Base64.getDecoder().decode(book.getImageUrl().split(",")[1]);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDto> updateBook(
            @PathVariable Long id,
            @Valid BookRequestDto bookRequestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/add-stock")
    public ResponseEntity<Void> addStock(@PathVariable Long id, @RequestParam int quantity) {
        bookService.addStock(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{bookId}/assign-author/{authorId}")
    public ResponseEntity<BookResponseDto> assignBookToAuthor(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.assignBookToAuthor(bookId, authorId));
    }
}