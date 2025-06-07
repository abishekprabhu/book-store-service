package com.hcltech.bookstore.service;

import com.hcltech.bookstore.dao.UserDAO.UserDAOService;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.mapper.User.UserMapper;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.User;
import com.hcltech.bookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDAOService userDAOService;

    /**
     * Registers a new author.
     */
    public Author registerAuthor(final AuthorRequestDTO dto) {
        log.info("Registering Author: {}", dto);
        Author author = userMapper.toAuthor(dto);
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        return userDAOService.save(author);
    }

    /**
     * Registers a new customer.
     */
    public Customer registerCustomer(final CustomerRequestDTO dto) {
        log.info("Registering Customer: {}", dto);
        Customer customer = userMapper.toCustomer(dto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return userDAOService.save(customer);
    }

    /**
     * Authenticates a user and generates JWT.
     */
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getUsername(),
                        authenticationRequestDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            return new AuthenticationResponseDto(jwt);
        }

        throw new UsernameNotFoundException("Invalid credentials");
    }
}
