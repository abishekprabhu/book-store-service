package com.hcltech.bookstore.service.AuthService;

import com.hcltech.bookstore.dao.UserDAO.UserDAOService;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerResponseDTO;
import com.hcltech.bookstore.mapper.User.UserMapper;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDAOService userDAOService;

    /**
     * Registers a new author.
     */
    public AuthorResponseDTO registerAuthor(final AuthorRequestDTO dto) {
        log.info("Attempting to register new author with username: {}", dto.getUsername());
        Author author = userMapper.toAuthor(dto);
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setRoles(new HashSet<>(Set.of("AUTHOR")));
        if (author.getBooks() != null) {
            log.debug("Author has {} books linked", author.getBooks().size());
            author.getBooks().forEach(book -> {
                book.setAuthor(author);
                log.debug("Book '{}' assigned to author '{}'", book.getTitle(), author.getUsername());
            });
        }
        userDAOService.save(author);
        AuthorResponseDTO authorResponseDTO = userMapper.toAuthorDTO(author);
        log.info("Author registered successfully: {}", authorResponseDTO.getUsername());
        return authorResponseDTO;
    }

    /**
     * Registers a new customer.
     */
    public CustomerResponseDTO registerCustomer(final CustomerRequestDTO dto) {
        log.info("Attempting to register new customer with username: {}", dto.getUsername());
        Customer customer = userMapper.toCustomer(dto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRoles(new HashSet<>(Set.of("CUSTOMER")));
        Customer savedCustomer = userDAOService.save(customer);
        log.info("Customer registered successfully: {}", savedCustomer);
        CustomerResponseDTO customerResponseDTO = userMapper.toCustomerDTO(savedCustomer);
        log.info("Customer response DTO created: {}", customerResponseDTO);
        return customerResponseDTO;
    }

    /**
     * Authenticates a user and generates JWT.
     */
    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        log.info("Attempting login for username: {}", authenticationRequestDto);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestDto.getUsername(),
                            authenticationRequestDto.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(authenticationRequestDto.getUsername());
                String jwt = jwtUtil.generateToken(userDetails);
                log.info("Login successful for user: {}", userDetails.getUsername()
                        + "ROLE : " + userDetails.getAuthorities());
                return new AuthenticationResponseDto(
                        userDetails.getUsername(),
                        jwt,
                        userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet())
                );

            }

        } catch (Exception e) {
            log.error("Authentication failed for user: {}", authenticationRequestDto.getUsername(), e);
        }

        throw new UsernameNotFoundException("Invalid credentials");
    }
}
