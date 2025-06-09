package com.hcltech.bookstore.dao.UserDAO;

import com.hcltech.bookstore.model.User;

public interface UserDAOService {

        <T extends User> T save(T user);
        User findByUsername(String username);

}
