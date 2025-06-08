package com.hcltech.bookstore.service.book;

import com.hcltech.bookstore.Exception.DuplicateEntityException;
import com.hcltech.bookstore.Exception.EntityNotFoundException;
import com.hcltech.bookstore.dao.authorDao.AuthorServiceDAO;
import com.hcltech.bookstore.dao.bookDao.BookServiceDAO;
import com.hcltech.bookstore.dto.BookDTO.BookRequestDTO;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;
import com.hcltech.bookstore.mapper.book.BookMapper;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final AuthorServiceDAO authorServiceDAO;
    private final BookServiceDAO bookServiceDAO;
    private final BookMapper bookMapper;

    @Override
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        log.info("Creating new book with title: {}", bookRequestDTO.getTitle());

        Book book = bookMapper.toEntity(bookRequestDTO);

        if (bookRequestDTO.getAuthorId() != null) {
            log.info("Assigning author with ID: {} to the book", bookRequestDTO.getAuthorId());
            Author author = authorServiceDAO.findById(bookRequestDTO.getAuthorId())
                    .orElseThrow(() -> new EntityNotFoundException("Author not found"));
            book.setAuthor(author);
        }
/*        if (bookRequestDTO.getImg() != null && !bookRequestDTO.getImg().isEmpty()) {
            try {
                book.setImg(bookRequestDTO.getImg().getBytes());
            } catch (Exception e) {
                log.error("Error while setting book image: {}", e.getMessage());
                throw new RuntimeException("Failed to set book image", e);
            }
        }*/

        Book savedBook = bookServiceDAO.save(book);
        log.info("Book created successfully with ID: {}", savedBook.getId());
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        log.info("Fetching all books");
        return bookServiceDAO.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        log.info("Fetching book with ID: {}", id);
        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found with ID: {}", id);
                    return new EntityNotFoundException("Book not found");
                });
        return bookMapper.toDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO bookRequestDTO) {
        log.info("Updating book with ID: {}", id);

        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found with ID: {}", id);
                    return new RuntimeException("Book not found");
                });

        book.setTitle(bookRequestDTO.getTitle());
        if (bookServiceDAO.existsByIsbn(bookRequestDTO.getIsbn())) {
            throw new DuplicateEntityException("A book with this ISBN already exists.");
        }

        book.setPrice(bookRequestDTO.getPrice());
        book.setDescription(bookRequestDTO.getDescription());

        if (bookRequestDTO.getAuthorId() != null) {
            log.info("Assigning new author with ID: {} to book", bookRequestDTO.getAuthorId());
            Author author = authorServiceDAO.findById(bookRequestDTO.getAuthorId())
                    .orElseThrow(() -> {
                        log.error("Author not found with ID: {}", bookRequestDTO.getAuthorId());
                        return new RuntimeException("Author not found");
                    });
            book.setAuthor(author);
        } else {
            log.info("Removing author from book with ID: {}", id);
            book.setAuthor(null);
        }

        Book updatedBook = bookServiceDAO.save(book);
        log.info("Book with ID: {} updated successfully", updatedBook.getId());
        return bookMapper.toDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Deleting book with ID: {}", id);
        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found with ID: {}", id);
                    return new EntityNotFoundException("Book not found");
                });
        bookServiceDAO.delete(book);
        log.info("Book with ID: {} deleted successfully", id);
    }

    @Override
    public BookResponseDTO assignBookToAuthor(Long bookId, Long authorId) {
        log.info("Assigning book with ID: {} to author with ID: {}", bookId, authorId);

        Book book = bookServiceDAO.findById(bookId)
                .orElseThrow(() -> {
                    log.error("Book not found with ID: {}", bookId);
                    return new EntityNotFoundException("Book not found");
                });

        if (book.getAuthor() != null) {
            log.warn("Book with ID: {} is already assigned to author ID: {}", bookId, book.getAuthor().getId());
            throw new DuplicateEntityException("Book is already assigned to an author and cannot be reassigned.");
        }

        Author author = authorServiceDAO.findById(authorId)
                .orElseThrow(() -> {
                    log.error("Author not found with ID: {}", authorId);
                    return new EntityNotFoundException("Author not found");
                });

        book.setAuthor(author);
        Book updatedBook = bookServiceDAO.save(book);
        log.info("Book with ID: {} successfully assigned to author with ID: {}", bookId, authorId);
        return bookMapper.toDTO(updatedBook);
    }

    @Override
    public void addStock(Long id, int quantity) {
        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setStock(book.getStock() + quantity);
        bookServiceDAO.save(book);
    }


}
