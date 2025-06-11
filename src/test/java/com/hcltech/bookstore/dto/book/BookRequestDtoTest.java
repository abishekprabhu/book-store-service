package com.hcltech.bookstore.dto.book;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.*;
import java.util.Set;
class BookRequestDtoTest {
    private Validator validator;
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    private BookRequestDto getValidDto() {
        return new BookRequestDto(
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
        BookRequestDto dto = getValidDto();
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "DTO should be valid");
    }
    @Test
    void testBlankTitle() {
        BookRequestDto dto = getValidDto();
        dto.setTitle("  ");
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }
    @Test
    void testBlankIsbn() {
        BookRequestDto dto = getValidDto();
        dto.setIsbn("");
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("isbn")));
    }
    @Test
    void testNegativePrice() {
        BookRequestDto dto = getValidDto();
        dto.setPrice(-10.00);
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }
    @Test
    void testInvalidPriceFormat() {
        BookRequestDto dto = getValidDto();
        dto.setPrice(123456789.123); // Exceeds digits allowed
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }
    @Test
    void testTooLongDescription() {
        BookRequestDto dto = getValidDto();
        dto.setDescription("a".repeat(1001));
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }
    @Test
    void testNegativeStock() {
        BookRequestDto dto = getValidDto();
        dto.setStock(-1);
        Set<ConstraintViolation<BookRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("stock")));
    }
}