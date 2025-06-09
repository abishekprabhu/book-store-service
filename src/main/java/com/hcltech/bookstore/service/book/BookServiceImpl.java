package com.hcltech.bookstore.service.book;

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

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final AuthorServiceDAO authorServiceDAO;
    private final BookServiceDAO bookServiceDAO;
    private final BookMapper bookMapper;

    private static final String AUTHOR_NOT_FOUND = "Author not found";
    private static final String BOOK_NOT_FOUND = "Book not found";


    @Override
    public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
        log.info("Creating new book with title: {}", bookRequestDTO.getTitle());

        Book book = bookMapper.toEntity(bookRequestDTO);

        if (bookRequestDTO.getAuthorId() != null) {
            log.info("Assigning author with ID: {} to the book", bookRequestDTO.getAuthorId());
            Author author = authorServiceDAO.findById(bookRequestDTO.getAuthorId())
                    .orElseThrow(() -> {
                        log.error("Author not found with ID: {}", bookRequestDTO.getAuthorId());
                        return new EntityNotFoundException(AUTHOR_NOT_FOUND);
                    });
            book.setAuthor(author);
        }else{
            book.setAuthor(null);
        }
        if(bookRequestDTO.getImg() == null)
            bookRequestDTO.setImg(null);

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
                    return new EntityNotFoundException(BOOK_NOT_FOUND);
                });
        return bookMapper.toDTO(book);
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setStock(dto.getStock());

        if (dto.getAuthorId() != null) {
            if (book.getAuthor() != null && !book.getAuthor().getId().equals(dto.getAuthorId())) {
                throw new IllegalStateException("Book is already assigned to another author.");
            }

            Author author = authorServiceDAO.findById(dto.getAuthorId())
                    .orElseThrow(() -> new EntityNotFoundException(AUTHOR_NOT_FOUND));
            book.setAuthor(author);
        }

        if (dto.getImg() != null && !dto.getImg().isEmpty()) {
            try {
                book.setImg(dto.getImg().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to update image", e);
            }
        }else{
            book.setImg(null);
        }

        Book updatedBook = bookServiceDAO.save(book);
        return bookMapper.toDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND));
        bookServiceDAO.delete(book);
    }

    @Override
    public void addStock(Long id, int quantity) {
        Book book = bookServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND));
        book.setStock(book.getStock() + quantity);
        bookServiceDAO.save(book);
    }

    @Override
    public BookResponseDTO assignBookToAuthor(Long bookId, Long authorId) {
        Book book = bookServiceDAO.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException(BOOK_NOT_FOUND));

        if (book.getAuthor() != null && !book.getAuthor().getId().equals(authorId)) {
            throw new IllegalStateException("Book is already assigned to another author.");
        }

        Author author = authorServiceDAO.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(AUTHOR_NOT_FOUND));

        book.setAuthor(author);
        return bookMapper.toDTO(bookServiceDAO.save(book));
    }

}
