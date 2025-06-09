package com.hcltech.bookstore.service.purchase;

import com.hcltech.bookstore.dao.bookDao.BookServiceDAO;
import com.hcltech.bookstore.dao.customerDao.CustomerServiceDAO;
import com.hcltech.bookstore.dao.purchasedDao.PurchasedBookDAO;
import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookRequestDTO;
import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookResponseDTO;
import com.hcltech.bookstore.mapper.purchase.PurchasedBookMapper;
import com.hcltech.bookstore.model.Book;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.PurchasedBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchasedBookServiceImpl implements PurchasedBookService{

    private final BookServiceDAO bookServiceDAO;
    private final CustomerServiceDAO customerServiceDAO;
    private final PurchasedBookDAO purchasedBookDAO;
    private final PurchasedBookMapper purchasedBookMapper;

    public PurchasedBookResponseDTO purchaseBook(PurchasedBookRequestDTO dto) {
        Book book = bookServiceDAO.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getStock() < dto.getQuantity()) {
            throw new IllegalStateException("Not enough stock available");
        }

        Customer customer = customerServiceDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Reduce stock
        book.setStock(book.getStock() - dto.getQuantity());
        bookServiceDAO.save(book);

        PurchasedBook purchase = new PurchasedBook();
        purchase.setBook(book);
        purchase.setCustomer(customer);
        purchase.setQuantity(dto.getQuantity());
        purchase.setPurchaseDate(LocalDateTime.now());

        return purchasedBookMapper.toDTO(purchasedBookDAO.save(purchase));
    }
}
