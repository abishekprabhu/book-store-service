package com.hcltech.bookstore.repository;

import com.hcltech.bookstore.model.PurchasedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedBookRepository extends JpaRepository<PurchasedBook, Long> {
}
