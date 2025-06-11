package com.hcltech.bookstore.dao.author;

import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceDaoImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceDaoImpl authorServiceDAO;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setBiography("Bio");
    }

    @Test
    void testFindById() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Optional<Author> result = authorServiceDAO.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testFindAll() {
        List<Author> authors = Arrays.asList(author, new Author());
        when(authorRepository.findAll()).thenReturn(authors);
        List<Author> result = authorServiceDAO.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        when(authorRepository.save(author)).thenReturn(author);
        Author saved = authorServiceDAO.save(author);
        assertNotNull(saved);
        assertEquals("John Doe", saved.getName());
    }

    @Test
    void testDelete() {
        doNothing().when(authorRepository).delete(author);
        authorServiceDAO.delete(author);
        verify(authorRepository, times(1)).delete(author);
    }
}