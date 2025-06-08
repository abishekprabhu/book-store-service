package com.hcltech.bookstore.dao.bookDao;

import com.hcltech.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceDAO {

    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book save(Book book);
    void delete(Book book);
    Boolean existsByIsbn(String isbn);
}
