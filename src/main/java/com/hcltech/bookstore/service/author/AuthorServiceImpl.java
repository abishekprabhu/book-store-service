package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.exception.AuthorNotFoundException;
import com.hcltech.bookstore.dao.author.AuthorServiceDao;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.mapper.author.AuthorMapper;
import com.hcltech.bookstore.model.Author;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorServiceDao authorServiceDAO;
    private final AuthorMapper authorMapper;

    private static final String AUTHOR_NOT_FOUND = "Author not found with id: ";

    @Override
    @Transactional
    public List<AuthorResponseDto> getAllAuthors() {
        return authorServiceDAO.findAll().stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public Optional<AuthorResponseDto> getAuthorById(Long id) {
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND+ id));
        return Optional.of(authorMapper.toDTO(author));
    }

    @Override
    @Transactional
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto updatedAuthor) {
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND + id));

        author.setName(updatedAuthor.getName());
        author.setBiography(updatedAuthor.getBiography());

        Author saved = authorServiceDAO.save(author);
        return authorMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AUTHOR_NOT_FOUND + id));

        // Detach author from books
        author.getBooks().forEach(book -> book.setAuthor(null));
        authorServiceDAO.save(author);

        authorServiceDAO.delete(author);
    }

}
