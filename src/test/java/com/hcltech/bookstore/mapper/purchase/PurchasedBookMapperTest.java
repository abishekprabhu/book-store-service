package com.hcltech.bookstore.mapper.purchase;
import com.hcltech.bookstore.dto.purchased.PurchasedBookResponseDto;
import com.hcltech.bookstore.model.Book;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.PurchasedBook;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PurchasedBookMapperTest {
    private final PurchasedBookMapper mapper = Mappers.getMapper(PurchasedBookMapper.class);
    @Test
    void testToDTO() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot Essentials");
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("Abishek Prabhu");
        PurchasedBook purchase = new PurchasedBook();
        purchase.setBook(book);
        purchase.setCustomer(customer);
        purchase.setQuantity(3);
        purchase.setPurchaseDate(LocalDateTime.now());
        // Act
        PurchasedBookResponseDto dto = mapper.toDTO(purchase);
        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getBookId());
        assertEquals("Spring Boot Essentials", dto.getBookTitle());
        assertEquals(2L, dto.getCustomerId());
        assertEquals("Abishek Prabhu", dto.getCustomerName());
        assertEquals(3, dto.getQuantity());
        assertNotNull(dto.getPurchaseDate());
    }
}