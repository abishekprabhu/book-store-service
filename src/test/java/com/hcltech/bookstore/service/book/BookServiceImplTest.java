package com.hcltech.bookstore.service.book;

import com.hcltech.bookstore.exception.EntityNotFoundException;
import com.hcltech.bookstore.dao.author.AuthorServiceDao;
import com.hcltech.bookstore.dao.book.BookServiceDao;
import com.hcltech.bookstore.dto.book.BookRequestDto;
import com.hcltech.bookstore.dto.book.BookResponseDto;
import com.hcltech.bookstore.mapper.book.BookMapper;
import com.hcltech.bookstore.model.Author;

import com.hcltech.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private AuthorServiceDao authorServiceDAO;

    @Mock
    private BookServiceDao bookServiceDAO;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequestDto bookRequestDTO;
    private Book book;
    private Author author;
    private BookResponseDto bookResponseDTO;

    @BeforeEach
    void setUp() {
        bookRequestDTO = new BookRequestDto(
                "Sample Book",
                "ISBN123",
                199.99,
                "Great book",
                10,
                1L,
                null
        );

        author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setIsbn("ISBN123");
        book.setPrice(199.99);
        book.setStock(10);
        book.setDescription("Great book");
        book.setAuthor(author);

        bookResponseDTO = new BookResponseDto(1L, "Sample Book", "ISBN123", 199.99, 10, "Great book", 1L, "John Doe", null);
    }

    @Test
    void testCreateBook_WithAuthor() {
        when(bookMapper.toEntity(bookRequestDTO)).thenReturn(book);
        when(authorServiceDAO.findById(1L)).thenReturn(Optional.of(author));
        when(bookServiceDAO.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookResponseDTO);

        BookResponseDto result = bookService.createBook(bookRequestDTO);

        assertNotNull(result);
        assertEquals("Sample Book", result.getTitle());
        verify(bookServiceDAO, times(1)).save(any(Book.class));
    }

    @Test
    void testGetAllBooks() {
        when(bookServiceDAO.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookResponseDTO);

        List<BookResponseDto> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Sample Book", result.get(0).getTitle());
    }

    @Test
    void testGetBookById_Success() {
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookResponseDTO);

        BookResponseDto result = bookService.getBookById(1L);

        assertEquals("Sample Book", result.getTitle());
    }

    @Test
    void testUpdateBook_Success() {
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        when(authorServiceDAO.findById(1L)).thenReturn(Optional.of(author));
        when(bookServiceDAO.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookResponseDTO);

        BookResponseDto result = bookService.updateBook(1L, bookRequestDTO);

        assertEquals("Sample Book", result.getTitle());
        verify(bookServiceDAO, times(1)).save(any(Book.class));
    }

    @Test
    void testDeleteBook_Success() {
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookServiceDAO).delete(book);

        assertDoesNotThrow(() -> bookService.deleteBook(1L));
        verify(bookServiceDAO, times(1)).delete(book);
    }

    @Test
    void testAddStock_Success() {
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        when(bookServiceDAO.save(any(Book.class))).thenReturn(book);

        bookService.addStock(1L, 5);

        assertEquals(15, book.getStock());
    }

    @Test
    void testAssignBookToAuthor_Success() {
        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));
        when(authorServiceDAO.findById(1L)).thenReturn(Optional.of(author));
        when(bookServiceDAO.save(book)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookResponseDTO);

        BookResponseDto result = bookService.assignBookToAuthor(1L, 1L);

        assertEquals("Sample Book", result.getTitle());
        assertEquals("John Doe", result.getAuthorName());
    }

    @Test
    void testAssignBookToAuthor_AlreadyAssignedToAnotherAuthor() {
        Author anotherAuthor = new Author();
        anotherAuthor.setId(2L);
        book.setAuthor(anotherAuthor);

        when(bookServiceDAO.findById(1L)).thenReturn(Optional.of(book));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> bookService.assignBookToAuthor(1L, 1L)
        );

        assertEquals("Book is already assigned to another author.", exception.getMessage());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookServiceDAO.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getBookById(99L)
        );

        assertEquals("Book not found", exception.getMessage());
    }
}