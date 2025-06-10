package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.config.SecurityTestConfig;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;
import com.hcltech.bookstore.service.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {BookController.class, SecurityTestConfig.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private BookResponseDTO bookResponseDTO;

    @BeforeEach
    void setup() {
        String base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString("image-data".getBytes());
        bookResponseDTO = new BookResponseDTO(
                1L,
                "Test Book",
                "ISBN123",
                199.99,
                5,
                "Test Description",
                1L,
                "John Author",
                base64Image
        );
    }

    @Test
    void testCreateBook() throws Exception {
        MockMultipartFile imgFile = new MockMultipartFile("img", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image-data".getBytes());

        MockMultipartFile title = new MockMultipartFile("title", "", "text/plain", "Test Book".getBytes());
        MockMultipartFile isbn = new MockMultipartFile("isbn", "", "text/plain", "ISBN123".getBytes());
        MockMultipartFile price = new MockMultipartFile("price", "", "text/plain", "199.99".getBytes());
        MockMultipartFile description = new MockMultipartFile("description", "", "text/plain", "Test Desc".getBytes());
        MockMultipartFile stock = new MockMultipartFile("stock", "", "text/plain", "5".getBytes());
        MockMultipartFile authorId = new MockMultipartFile("authorId", "", "text/plain", "1".getBytes());

        when(bookService.createBook(any())).thenReturn(bookResponseDTO);

        mockMvc.perform(multipart("/api/v1/books")
                        .file(new MockMultipartFile("img", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image-data".getBytes()))
                        .param("title", "Test Book")
                        .param("isbn", "ISBN123")
                        .param("price", "199.99")
                        .param("description", "Test Desc")
                        .param("stock", "5")
                        .param("authorId", "1")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.isbn").value("ISBN123"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(bookResponseDTO));

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Book"));
    }

    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookResponseDTO);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void testGetBookImage() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(bookResponseDTO);

        mockMvc.perform(get("/api/v1/books/1/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    void testUpdateBook() throws Exception {
        MockMultipartFile imgFile = new MockMultipartFile("img", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "image-data".getBytes());

        MockMultipartFile title = new MockMultipartFile("title", "", "text/plain", "Updated Book".getBytes());
        MockMultipartFile isbn = new MockMultipartFile("isbn", "", "text/plain", "ISBN123".getBytes());
        MockMultipartFile price = new MockMultipartFile("price", "", "text/plain", "249.99".getBytes());
        MockMultipartFile description = new MockMultipartFile("description", "", "text/plain", "Updated Desc".getBytes());
        MockMultipartFile stock = new MockMultipartFile("stock", "", "text/plain", "10".getBytes());
        MockMultipartFile authorId = new MockMultipartFile("authorId", "", "text/plain", "1".getBytes());

        when(bookService.updateBook(eq(1L), any())).thenReturn(bookResponseDTO);

        mockMvc.perform(multipart("/api/v1/books/1")
                        .file(imgFile)
                        .param("title", "Updated Book")
                        .param("isbn", "ISBN123")
                        .param("price", "249.99")
                        .param("description", "Updated Desc")
                        .param("stock", "10")
                        .param("authorId", "1")
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/api/v1/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    void testAddStock() throws Exception {
        mockMvc.perform(patch("/api/v1/books/1/add-stock")
                        .param("quantity", "5"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).addStock(1L, 5);
    }

    @Test
    void testAssignBookToAuthor() throws Exception {
        when(bookService.assignBookToAuthor(1L, 1L)).thenReturn(bookResponseDTO);

        mockMvc.perform(patch("/api/v1/books/1/assign-author/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorName").value("John Author"));
    }
}