package com.hcltech.bookstore.dao.purchased;

import com.hcltech.bookstore.model.PurchasedBook;
import com.hcltech.bookstore.repository.PurchasedBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchasedBookServiceDaoImplTest {

    private PurchasedBookRepository purchasedBookRepository;
    private PurchasedBookServiceDaoImpl purchasedBookDAO;

    @BeforeEach
    void setUp() {
        purchasedBookRepository = mock(PurchasedBookRepository.class);
        purchasedBookDAO = new PurchasedBookServiceDaoImpl(purchasedBookRepository);
    }

    @Test
    void testFindById() {
        PurchasedBook book = new PurchasedBook();
        book.setId(1L);
        when(purchasedBookRepository.findById(1L)).thenReturn(Optional.of(book));
        Optional<PurchasedBook> result = purchasedBookDAO.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testSave() {
        PurchasedBook book = new PurchasedBook();
        book.setId(2L);
        book.setQuantity(5);
        book.setPurchaseDate(LocalDateTime.now());
        when(purchasedBookRepository.save(book)).thenReturn(book);
        PurchasedBook saved = purchasedBookDAO.save(book);
        assertNotNull(saved);
        assertEquals(2L, saved.getId());
        assertEquals(5, saved.getQuantity());
    }

    @Test
    void testDelete() {
        PurchasedBook book = new PurchasedBook();
        book.setId(3L);
        doNothing().when(purchasedBookRepository).delete(book);
        purchasedBookDAO.delete(book);
        verify(purchasedBookRepository, times(1)).delete(book);
    }

    @Test
    void testFindAll() {
        PurchasedBook b1 = new PurchasedBook();
        b1.setId(1L);
        PurchasedBook b2 = new PurchasedBook();
        b2.setId(2L);
        when(purchasedBookRepository.findAll()).thenReturn(Arrays.asList(b1, b2));
        List<PurchasedBook> result = purchasedBookDAO.findAll();
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }
}