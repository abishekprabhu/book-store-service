package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorResponseDTO> getAllAuthors();
    Optional<AuthorResponseDTO> getAuthorById(Long id);
    AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO updatedAuthor);
    void deleteAuthor(Long id);
}
