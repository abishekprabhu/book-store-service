package com.hcltech.bookstore.service.authentication;

import com.hcltech.bookstore.dao.user.UserServiceDao;
import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.mapper.user.UserMapper;
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
    private final UserServiceDao userServiceDao;

    public AuthorResponseDto registerAuthor(final AuthorRequestDto dto) {
        log.info("Attempting to register new author with username: {}", dto.getUsername());
        Author author = userMapper.toAuthor(dto);
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setRoles(new HashSet<>(Set.of("AUTHOR")));
        userServiceDao.save(author);
        AuthorResponseDto authorResponseDTO = userMapper.toAuthorDTO(author);
        log.info("Author registered successfully: {}", authorResponseDTO.getUsername());
        return authorResponseDTO;
    }

    public CustomerResponseDto registerCustomer(final CustomerRequestDto dto) {
        log.info("Attempting to register new customer with username: {}", dto.getUsername());
        Customer customer = userMapper.toCustomer(dto);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRoles(new HashSet<>(Set.of("CUSTOMER")));
        Customer savedCustomer = userServiceDao.save(customer);
        log.info("Customer registered successfully: {}", savedCustomer);
        CustomerResponseDto customerResponseDTO = userMapper.toCustomerDTO(savedCustomer);
        log.info("Customer response DTO created: {}", customerResponseDTO);
        return customerResponseDTO;
    }

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
