package com.hcltech.bookstore.service.purchase;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchasedBookServiceImplTest {
    @Mock
    private BookServiceDao bookServiceDAO;
    @Mock
    private CustomerServiceDao customerServiceDAO;
    @Mock
    private PurchasedBookServiceDao purchasedBookServiceDAO;
    @Mock
    private PurchasedBookMapper purchasedBookMapper;
    @InjectMocks
    private PurchasedBookServiceImpl purchasedBookService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testPurchaseBook_Success() {
        PurchasedBookRequestDto request = new PurchasedBookRequestDto();
        request.setBookId(1L);
        request.setCustomerId(2L);
        request.setQuantity(2);
        Book book = new Book();
        book.setId(1L);
        book.setStock(5);
        book.setTitle("Test Book");
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("Test Customer");
        PurchasedBook purchase = new PurchasedBook();
        purchase.setBook(book);
        purchase.setCustomer(customer);
        purchase.setQuantity(2);
        purchase.setPurchaseDate(LocalDateTime.now());
        PurchasedBookResponseDto responseDTO = new PurchasedBookResponseDto();
        responseDTO.setBookId(1L);
        responseDTO.setCustomerId(2L);
        responseDTO.setQuantity(2);
        responseDTO.setBookTitle("Test Book");
        responseDTO.setCustomerName("Test Customer");
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        when(customerServiceDAO.findById(2L)).thenReturn(Optional.of(customer));
        when(purchasedBookServiceDAO.save(any(PurchasedBook.class))).thenReturn(purchase);
        when(bookServiceDAO.save(any(Book.class))).thenReturn(book);
        when(purchasedBookMapper.toDTO(any(PurchasedBook.class))).thenReturn(responseDTO);
        PurchasedBookResponseDto result = purchasedBookService.purchaseBook(request);
        assertNotNull(result);
        assertEquals(1L, result.getBookId());
        assertEquals(2L, result.getCustomerId());
        assertEquals(2, result.getQuantity());
        verify(bookServiceDAO).save(book);
        verify(purchasedBookServiceDAO).save(any(PurchasedBook.class));
    }
    @Test
    void testPurchaseBook_BookNotFound() {
        PurchasedBookRequestDto request = new PurchasedBookRequestDto();
        request.setBookId(1L);
        request.setCustomerId(2L);
        request.setQuantity(1);
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                purchasedBookService.purchaseBook(request));
        assertEquals("Book not found", exception.getMessage());
    }
    @Test
    void testPurchaseBook_CustomerNotFound() {
        PurchasedBookRequestDto request = new PurchasedBookRequestDto();
        request.setBookId(1L);
        request.setCustomerId(2L);
        request.setQuantity(1);
        Book book = new Book();
        book.setId(1L);
        book.setStock(10);
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        when(customerServiceDAO.findById(2L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                purchasedBookService.purchaseBook(request));
        assertEquals("Customer not found", exception.getMessage());
    }
    @Test
    void testPurchaseBook_InsufficientStock() {
        PurchasedBookRequestDto request = new PurchasedBookRequestDto();
        request.setBookId(1L);
        request.setCustomerId(2L);
        request.setQuantity(10);
        Book book = new Book();
        book.setId(1L);
        book.setStock(5);
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () ->
                purchasedBookService.purchaseBook(request));
        assertEquals("Not enough stock available", exception.getMessage());
    }
}