package com.hcltech.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcltech.bookstore.config.SecurityTestConfig;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.service.author.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {AuthorController.class, SecurityTestConfig.class})
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAuthors() throws Exception {
        List<AuthorResponseDto> authors = List.of(new AuthorResponseDto());
        authors.getFirst().setUsername("author1");
        authors.getFirst().setName("Author 1");
        authors.getFirst().setBiography("Bio 1");

        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/api/v1/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("author1"))
                .andExpect(jsonPath("$[0].name").value("Author 1"))
                .andExpect(jsonPath("$[0].biography").value("Bio 1"));
    }

    @Test
    void testGetAuthorById_Found() throws Exception {
        AuthorResponseDto author = new AuthorResponseDto();
        author.setUsername("author1");
        author.setName("Author 1");
        author.setBiography("Bio 1");

        when(authorService.getAuthorById(1L)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/api/v1/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("author1"))
                .andExpect(jsonPath("$.name").value("Author 1"))
                .andExpect(jsonPath("$.biography").value("Bio 1"));
    }

    @Test
    void testGetAuthorById_NotFound() throws Exception {
        when(authorService.getAuthorById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/authors/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAuthor() throws Exception {
        AuthorRequestDto request = new AuthorRequestDto();
        request.setUsername("author1");
        request.setPassword("Password@1");
        request.setName("Updated Author");
        request.setBiography("Updated Bio");

        AuthorResponseDto response = new AuthorResponseDto();
        response.setUsername("author1");
        response.setName("Updated Author");
        response.setBiography("Updated Bio");

        when(authorService.updateAuthor(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("author1"))
                .andExpect(jsonPath("$.name").value("Updated Author"))
                .andExpect(jsonPath("$.biography").value("Updated Bio"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isNoContent());
    }
}