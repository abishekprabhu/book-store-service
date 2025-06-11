package com.hcltech.bookstore.dao.purchased;

import com.hcltech.bookstore.model.PurchasedBook;
import com.hcltech.bookstore.repository.PurchasedBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchasedBookServiceDaoImpl implements PurchasedBookServiceDao {

    private final PurchasedBookRepository purchasedBookRepository;

    @Override
    public Optional<PurchasedBook> findById(Long id) {
        return purchasedBookRepository.findById(id);
    }

    @Override
    public PurchasedBook save(PurchasedBook purchasedBook) {
        return purchasedBookRepository.save(purchasedBook);
    }

    @Override
    public void delete(PurchasedBook purchasedBook) {
        purchasedBookRepository.delete(purchasedBook);
    }

    @Override
    public List<PurchasedBook> findAll() {
        return purchasedBookRepository.findAll();
    }
}
