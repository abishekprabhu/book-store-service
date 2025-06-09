package com.hcltech.bookstore.dao.purchasedDao;

import com.hcltech.bookstore.model.PurchasedBook;

import java.util.List;
import java.util.Optional;

public interface PurchasedBookDAO {
    Optional<PurchasedBook> findById(Long id);
    PurchasedBook save(PurchasedBook purchasedBook);
    void delete(PurchasedBook purchasedBook);
    List<PurchasedBook> findAll();
}
