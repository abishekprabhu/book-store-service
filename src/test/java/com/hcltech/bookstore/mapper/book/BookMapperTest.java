package com.hcltech.bookstore.mapper.book;

import com.hcltech.bookstore.Exception.CustomException;
import com.hcltech.bookstore.dto.BookDTO.BookRequestDTO;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class BookMapperTest {
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    @Test
    void testToDTO() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot");
        book.setIsbn("12345");
        book.setPrice(199.99);
        book.setStock(5);
        book.setDescription("A book on Spring Boot");
        book.setImg("SampleImageData".getBytes());
        Author author = new Author();
        author.setId(100L);
        author.setName("John Doe");
        book.setAuthor(author);
        BookResponseDTO dto = bookMapper.toDTO(book);
        assertEquals("Spring Boot", dto.getTitle());
        assertEquals("12345", dto.getIsbn());
        assertEquals(199.99, dto.getPrice());
        assertEquals(5, dto.getStock());
        assertEquals("A book on Spring Boot", dto.getDescription());
        assertEquals(100L, dto.getAuthorId());
        assertEquals("John Doe", dto.getAuthorName());
        assertNotNull(dto.getImageUrl());
        assertTrue(dto.getImageUrl().startsWith("data:image/jpeg;base64,"));
    }
    @Test
    void testToEntity_withImage() {
        byte[] imageBytes = "Mock Image Content".getBytes();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("img", "image.jpg", "image/jpeg", imageBytes);
        BookRequestDTO dto = new BookRequestDTO();
        dto.setTitle("New Book");
        dto.setIsbn("ISBN001");
        dto.setPrice(29.99);
        dto.setDescription("Great book");
        dto.setStock(15);
        dto.setAuthorId(101L);
        dto.setImg(mockMultipartFile);
        Book book = bookMapper.toEntity(dto);
        assertEquals("New Book", book.getTitle());
        assertEquals("ISBN001", book.getIsbn());
        assertEquals(29.99, book.getPrice());
        assertEquals("Great book", book.getDescription());
        assertEquals(15, book.getStock());
        assertArrayEquals(imageBytes, book.getImg());
    }
    @Test
    void testMultipartToBytes_withNull() {
        byte[] result = BookMapper.multipartToBytes(null);
        assertNull(result);
    }
    @Test
    void testMultipartToBytes_withError() {
        MultipartFile file = new MultipartFile() {
            @Override public String getName() { return "error"; }
            @Override public String getOriginalFilename() { return "error.jpg"; }
            @Override public String getContentType() { return "image/jpeg"; }
            @Override public boolean isEmpty() { return false; }
            @Override public long getSize() { return 0; }
            @Override public byte[] getBytes() throws IOException {
                throw new IOException("Mock IO error");
            }
            @Override public InputStream getInputStream() throws IOException { throw new IOException(); }
            @Override public void transferTo(File dest) { }
        };
        CustomException exception = assertThrows(CustomException.class, () -> BookMapper.multipartToBytes(file));
        assertTrue(exception.getMessage().contains("Failed to convert image"));
    }
}