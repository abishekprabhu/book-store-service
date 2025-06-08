package com.hcltech.bookstore.dao.authorDao;

import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceDAOImpl implements AuthorServiceDAO {

    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}