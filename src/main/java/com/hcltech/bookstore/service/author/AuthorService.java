package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorResponseDto> getAllAuthors();
    Optional<AuthorResponseDto> getAuthorById(Long id);
    AuthorResponseDto updateAuthor(Long id, AuthorRequestDto updatedAuthor);
    void deleteAuthor(Long id);
}
