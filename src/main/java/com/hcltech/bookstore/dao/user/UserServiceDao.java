package com.hcltech.bookstore.dao.user;

import com.hcltech.bookstore.model.User;

public interface UserServiceDao {

        <T extends User> T save(T user);
        User findByUsername(String username);

}
