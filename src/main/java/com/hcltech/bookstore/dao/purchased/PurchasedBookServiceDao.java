package com.hcltech.bookstore.dao.purchased;

import com.hcltech.bookstore.model.PurchasedBook;

import java.util.List;
import java.util.Optional;

public interface PurchasedBookServiceDao {
    Optional<PurchasedBook> findById(Long id);
    PurchasedBook save(PurchasedBook purchasedBook);
    void delete(PurchasedBook purchasedBook);
    List<PurchasedBook> findAll();
}
