package com.hcltech.bookstore.service.purchase;

import com.hcltech.bookstore.exception.BookNotFoundException;
import com.hcltech.bookstore.exception.EntityNotFoundException;
import com.hcltech.bookstore.exception.InsufficientStockException;
import com.hcltech.bookstore.dao.book.BookServiceDao;
import com.hcltech.bookstore.dao.customer.CustomerServiceDao;
import com.hcltech.bookstore.dao.purchased.PurchasedBookServiceDao;
import com.hcltech.bookstore.dto.purchased.PurchasedBookRequestDto;
import com.hcltech.bookstore.dto.purchased.PurchasedBookResponseDto;
import com.hcltech.bookstore.mapper.purchase.PurchasedBookMapper;
import com.hcltech.bookstore.model.Book;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.PurchasedBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PurchasedBookServiceImpl implements PurchasedBookService{

    private final BookServiceDao bookServiceDAO;
    private final CustomerServiceDao customerServiceDAO;
    private final PurchasedBookServiceDao purchasedBookServiceDAO;
    private final PurchasedBookMapper purchasedBookMapper;

    @Override
    @Transactional
    public PurchasedBookResponseDto purchaseBook(PurchasedBookRequestDto dto) {
        Book book = bookServiceDAO.findById(dto.getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (book.getStock() < dto.getQuantity()) {
            throw new InsufficientStockException("Not enough stock available");
        }

        Customer customer = customerServiceDAO.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        // Reduce stock
        book.setStock(book.getStock() - dto.getQuantity());
        bookServiceDAO.save(book);

        PurchasedBook purchase = new PurchasedBook();
        purchase.setBook(book);
        purchase.setCustomer(customer);
        purchase.setQuantity(dto.getQuantity());
        purchase.setPurchaseDate(LocalDateTime.now());

        return purchasedBookMapper.toDTO(purchasedBookServiceDAO.save(purchase));
    }
}
