package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<Author> getAuthorById(Long id);
    List<Author> getAllAuthors();
    Author updateAuthor(Long id, Author authorDetails);
    void deleteAuthor(Long id);
}
