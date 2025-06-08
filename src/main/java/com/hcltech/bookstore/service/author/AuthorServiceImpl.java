package com.hcltech.bookstore.service.author;

import com.hcltech.bookstore.Exception.EntityNotFoundException;
import com.hcltech.bookstore.dao.authorDao.AuthorServiceDAO;
import com.hcltech.bookstore.model.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorServiceDAO authorServiceDAO;


    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorServiceDAO.findById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorServiceDAO.findAll();
    }

    @Override
    public Author updateAuthor(Long id, Author authorDetails) {
        Author existingAuthor = authorServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + id));

        existingAuthor.setName(authorDetails.getName());
        existingAuthor.setBiography(authorDetails.getBiography());

        // Handle books update
        // Clear existing books and add new ones, maintaining bidirectional link
        existingAuthor.getBooks().clear();

        if (authorDetails.getBooks() != null) {
            authorDetails.getBooks().forEach(book -> {
                book.setAuthor(existingAuthor);
                existingAuthor.getBooks().add(book);
            });
        }

        return authorServiceDAO.save(existingAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author existingAuthor = authorServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id " + id));
        authorServiceDAO.delete(existingAuthor);
    }
}
