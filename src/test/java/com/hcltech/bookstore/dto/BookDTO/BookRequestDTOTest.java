package com.hcltech.bookstore.dto.BookDTO;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.*;
import java.util.Set;
class BookRequestDTOTest {
    private Validator validator;
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    private BookRequestDTO getValidDto() {
        return new BookRequestDTO(
                "Valid Title",
                "1234567890",
                100.50,
                "This is a valid description",
                10,
                1L,
                null
        );
    }
    @Test
    void testValidDTO() {
        BookRequestDTO dto = getValidDto();
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO should be valid");
    }
    @Test
    void testBlankTitle() {
        BookRequestDTO dto = getValidDto();
        dto.setTitle("  ");
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }
    @Test
    void testBlankIsbn() {
        BookRequestDTO dto = getValidDto();
        dto.setIsbn("");
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn")));
    }
    @Test
    void testNegativePrice() {
        BookRequestDTO dto = getValidDto();
        dto.setPrice(-10.00);
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }
    @Test
    void testInvalidPriceFormat() {
        BookRequestDTO dto = getValidDto();
        dto.setPrice(123456789.123); // Exceeds digits allowed
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }
    @Test
    void testTooLongDescription() {
        BookRequestDTO dto = getValidDto();
        dto.setDescription("a".repeat(1001));
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }
    @Test
    void testNegativeStock() {
        BookRequestDTO dto = getValidDto();
        dto.setStock(-1);
        Set<ConstraintViolation<BookRequestDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("stock")));
    }
}