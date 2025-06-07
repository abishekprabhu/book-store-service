package com.hcltech.bookstore.repository;

import com.hcltech.bookstore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByUsername(String username);
}
