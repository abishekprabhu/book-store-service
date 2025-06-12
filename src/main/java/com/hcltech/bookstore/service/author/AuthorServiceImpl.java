package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.exception.AuthorNotFoundException;
import com.hcltech.bookstore.dao.author.AuthorServiceDao;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.mapper.author.AuthorMapper;
import com.hcltech.bookstore.model.Author;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorServiceDao authorServiceDAO;
    private final AuthorMapper authorMapper;

    private static final String AUTHOR_NOT_FOUND = "Author not found with id: ";

    @Override
    @Transactional
    public List<AuthorResponseDto> getAllAuthors() {
        log.info("Fetching all authors");
        List<AuthorResponseDto> authors = authorServiceDAO.findAll().stream()
                .map(authorMapper::toDTO)
                .toList();
        log.debug("Total authors found: {}", authors.size());
        return authors;
    }

    @Override
    @Transactional
    public Optional<AuthorResponseDto> getAuthorById(Long id) {
        log.info("Fetching author by ID: {}", id);
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with ID: {}", id);
                    return new AuthorNotFoundException(AUTHOR_NOT_FOUND + id);
                });
        log.debug("Author found: {}", author.getName());
        return Optional.of(authorMapper.toDTO(author));
    }

    @Override
    @Transactional
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto updatedAuthor) {
        log.info("Updating author with ID: {}", id);
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found for update with ID: {}", id);
                    return new AuthorNotFoundException(AUTHOR_NOT_FOUND + id);
                });

        author.setName(updatedAuthor.getName());
        author.setBiography(updatedAuthor.getBiography());

        Author saved = authorServiceDAO.save(author);
        log.debug("Author updated: {}", saved);
        return authorMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        log.info("Deleting author with ID: {}", id);
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found for deletion with ID: {}", id);
                    return new AuthorNotFoundException(AUTHOR_NOT_FOUND + id);
                });

        // Detach author from books
        author.getBooks().forEach(book -> book.setAuthor(null));
        authorServiceDAO.save(author);
        log.debug("Detached books from author with ID: {}", id);

        authorServiceDAO.delete(author);
        log.info("Author with ID {} deleted", id);
    }

}
