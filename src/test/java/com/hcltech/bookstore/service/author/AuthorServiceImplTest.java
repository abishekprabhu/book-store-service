package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.dao.authorDao.AuthorServiceDAO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.mapper.author.AuthorMapper;
import com.hcltech.bookstore.model.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Mock
    private AuthorServiceDAO authorServiceDAO;
    @Mock
    private AuthorMapper authorMapper;

    @Test
    void testGetAllAuthors() {
        Author author = new Author();
        author.setUsername("author1");
        author.setName("Author");
        author.setBiography("Bio");
        author.setBooks(List.of());

        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setUsername("author1");
        dto.setName("Author");
        dto.setBiography("Bio");

        List<Author> authors = List.of(author);

        when(authorServiceDAO.findAll()).thenReturn(authors);
        when(authorMapper.toDTO(any())).thenReturn(dto);

        List<AuthorResponseDTO> result = authorService.getAllAuthors();
        assertEquals(1, result.size());
        assertEquals("Author", result.getFirst().getName());
    }

    @Test
    void testGetAuthorById_Found() {
        Author author = new Author();
        author.setUsername("author1");
        author.setName("Author");
        author.setBiography("Bio");
        author.setBooks(List.of());

        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setUsername("author1");
        dto.setName("Author");
        dto.setBiography("Bio");

        when(authorServiceDAO.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toDTO(author)).thenReturn(dto);

        Optional<AuthorResponseDTO> result = authorService.getAuthorById(1L);
        assertTrue(result.isPresent());
        assertEquals("Author", result.get().getName());
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setUsername("author1");
        author.setName("Old");
        author.setBiography("Old Bio");
        author.setBooks(List.of());

        AuthorRequestDTO requestDTO = new AuthorRequestDTO();
        requestDTO.setUsername("author1");
        requestDTO.setPassword("Password@1");
        requestDTO.setName("New");
        requestDTO.setBiography("New Bio");

        Author updatedAuthor = new Author();
        updatedAuthor.setUsername("author1");
        updatedAuthor.setName("New");
        updatedAuthor.setBiography("New Bio");
        updatedAuthor.setBooks(List.of());

        AuthorResponseDTO responseDTO = new AuthorResponseDTO();
        responseDTO.setUsername("author1");
        responseDTO.setName("New");
        responseDTO.setBiography("New Bio");

        when(authorServiceDAO.findById(1L)).thenReturn(Optional.of(author));
        when(authorServiceDAO.save(any())).thenReturn(updatedAuthor);
        when(authorMapper.toDTO(updatedAuthor)).thenReturn(responseDTO);

        AuthorResponseDTO result = authorService.updateAuthor(1L, requestDTO);
        assertEquals("New", result.getName());
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setUsername("author1");
        author.setName("Name");
        author.setBiography("Bio");
        author.setBooks(new ArrayList<>());

        when(authorServiceDAO.findById(1L)).thenReturn(Optional.of(author));
        authorService.deleteAuthor(1L);
        verify(authorServiceDAO).save(author); // Detach
        verify(authorServiceDAO).delete(author); // Delete
    }
}