package com.hcltech.bookstore.dao.UserDAO;

import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.User;
import com.hcltech.bookstore.repository.AuthorRepository;
import com.hcltech.bookstore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDAOServiceImpl implements UserDAOService{

    private final AuthorRepository authorRepository;
    private final CustomerRepository customerRepository;

    @SuppressWarnings("unchecked")
    public <T    extends User> T save(T user) {
        if (user instanceof Author) {
            return (T) authorRepository.save((Author) user);
        } else if (user instanceof Customer) {
            return (T) customerRepository.save((Customer) user);
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getSimpleName());
        }
    }

    public User findByUsername(String username) {
        return authorRepository.findByUsername(username)
                .map(user -> (User) user)
                .orElseGet(() -> (User) customerRepository.findByUsername(username).orElse(null));
    }

}

