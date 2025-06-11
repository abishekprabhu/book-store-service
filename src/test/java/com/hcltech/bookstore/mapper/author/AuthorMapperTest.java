package com.hcltech.bookstore.mapper.author;

import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.dto.book.BookSummaryDto;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Base64;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Test
    void testToDTO() {
        // Prepare test data
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsbn("1234567890");
        book.setPrice(19.99);
        book.setImg(new byte[]{1, 2, 3, 4});

        Author author = new Author();
        author.setUsername("author1");
        author.setName("John Doe");
        author.setBiography("Bio");
        author.setRoles(Set.of("AUTHOR"));
        author.setBooks(List.of(book));

        // Map to DTO
        AuthorResponseDto dto = authorMapper.toDTO(author);

        // Assertions
        assertEquals("author1", dto.getUsername());
        assertEquals("John Doe", dto.getName());
        assertEquals("Bio", dto.getBiography());
        assertTrue(dto.getRoles().contains("AUTHOR"));
        assertNotNull(dto.getBooks());
        assertEquals(1, dto.getBooks().size());

        BookSummaryDto bookDTO = dto.getBooks().get(0);
        assertEquals("Test Book", bookDTO.getTitle());
        assertEquals("1234567890", bookDTO.getIsbn());
        assertEquals(19.99, bookDTO.getPrice());
        assertNotNull(bookDTO.getImageUrl());
        assertTrue(bookDTO.getImageUrl().startsWith("data:image/jpeg;base64,"));
        String expectedBase64 = Base64.getEncoder().encodeToString(new byte[]{1, 2, 3, 4});
        assertTrue(bookDTO.getImageUrl().contains(expectedBase64));
    }

    @Test
    void testToBase64Image_Null() {
        String result = authorMapper.toBase64Image(null);
        assertNull(result);
    }
}