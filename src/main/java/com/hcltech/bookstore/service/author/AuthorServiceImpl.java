package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.dao.authorDao.AuthorServiceDAO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
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

    private final AuthorServiceDAO authorServiceDAO;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorServiceDAO.findAll().stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public Optional<AuthorResponseDTO> getAuthorById(Long id) {
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        return Optional.of(authorMapper.toDTO(author));
    }

    @Override
    @Transactional
    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO updatedAuthor) {
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));

        author.setName(updatedAuthor.getName());
        author.setBiography(updatedAuthor.getBiography());

        Author saved = authorServiceDAO.save(author);
        return authorMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));

        // Detach author from books
        author.getBooks().forEach(book -> book.setAuthor(null));
        authorServiceDAO.save(author); // Save changes to books

        authorServiceDAO.delete(author); // Now safe to delete
    }

}
