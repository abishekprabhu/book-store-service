package com.hcltech.bookstore.dao.author;

import com.hcltech.bookstore.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorServiceDao {

    Optional<Author> findById(Long id);
    Author save(Author author);
    void delete(Author author);
    List<Author> findAll();
}
