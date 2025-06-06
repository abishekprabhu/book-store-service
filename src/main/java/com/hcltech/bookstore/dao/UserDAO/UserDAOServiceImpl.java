package com.hcltech.bookstore.dao.UserDAO;

import com.hcltech.bookstore.model.User;
import com.hcltech.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDAOServiceImpl implements UserDAOService{

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}

