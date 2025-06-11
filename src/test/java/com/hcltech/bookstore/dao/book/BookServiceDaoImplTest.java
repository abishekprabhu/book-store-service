package com.hcltech.bookstore.dao.book;

import com.hcltech.bookstore.model.Book;
import com.hcltech.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceDaoImplTest {

    private BookRepository bookRepository;
    private BookServiceDaoImpl bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookServiceDaoImpl(bookRepository);
    }

    @Test
    void testFindById() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(bookRepository).findById(1L);
    }

    @Test
    void testFindAll() {
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> mockList = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(mockList);

        List<Book> result = bookService.findAll();

        assertEquals(2, result.size());
        verify(bookRepository).findAll();
    }

    @Test
    void testSave() {
        Book book = new Book();
        book.setTitle("Test Book");

        when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.save(book);

        assertEquals("Test Book", saved.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void testDelete() {
        Book book = new Book();
        book.setId(99L);

        bookService.delete(book);

        verify(bookRepository).delete(book);
    }

    @Test
    void testFindById_NotFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Book> result = bookService.findById(999L);

        assertFalse(result.isPresent());
        verify(bookRepository).findById(999L);
    }

    @Test
    void testFindAll_EmptyList() {
        when(bookRepository.findAll()).thenReturn(List.of());
        List<Book> result = bookService.findAll();
        assertTrue(result.isEmpty());
        verify(bookRepository).findAll();
    }

    @Test
    void testSave_ExistingBook() {
        Book book = new Book();
        book.setId(10L);
        book.setTitle("Updated Title");
        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.save(book);
        assertEquals(10L, result.getId());
        assertEquals("Updated Title", result.getTitle());
        verify(bookRepository).save(book);
    }

    @Test
    void testDelete_VerifyDeletedBook() {
        Book book = new Book();
        book.setId(5L);
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        bookService.delete(book);
        verify(bookRepository).delete(captor.capture());
        assertEquals(5L, captor.getValue().getId());
    }
}