package com.hcltech.bookstore.repository;

import com.hcltech.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Boolean existsByIsbn(String isbn);
}
