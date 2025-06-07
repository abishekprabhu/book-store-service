package com.hcltech.bookstore.dao.UserDAO;

import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.User;

import java.util.Optional;

public interface UserDAOService {

        <T extends User> T save(T user);
        User findByUsername(String username);

}
