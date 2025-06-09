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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock private JpaUserDetailsService jpaUserDetailsService;
    @Mock private JwtUtil jwtUtil;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserMapper userMapper;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private UserDAOService userDAOService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private AuthorRequestDTO authorRequestDTO;
    private Author author;
    private AuthorResponseDTO authorResponseDTO;

    private CustomerRequestDTO customerRequestDTO;
    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;

    private AuthenticationRequestDto authRequestDto;

    @BeforeEach
    void setup() {
        authorRequestDTO = new AuthorRequestDTO();
        authorRequestDTO.setUsername("author");
        authorRequestDTO.setPassword("password");

        author = new Author();
        author.setUsername("author");
        author.setPassword("encoded-password");

        authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setUsername("author");

        customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setUsername("customer");
        customerRequestDTO.setPassword("password");

        customer = new Customer();
        customer.setUsername("customer");
        customer.setPassword("encoded-password");

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setUsername("customer");

        authRequestDto = new AuthenticationRequestDto();
        authRequestDto.setUsername("author");
        authRequestDto.setPassword("password");
    }

    @Test
    void testRegisterAuthor() {
        when(userMapper.toAuthor(authorRequestDTO)).thenReturn(author);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
        when(userMapper.toAuthorDTO(author)).thenReturn(authorResponseDTO);

        AuthorResponseDTO result = authenticationService.registerAuthor(authorRequestDTO);

        assertNotNull(result);
        assertEquals("author", result.getUsername());
        verify(userDAOService, times(1)).save(author);
    }

    @Test
    void testRegisterCustomer() {
        when(userMapper.toCustomer(customerRequestDTO)).thenReturn(customer);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
        when(userDAOService.save(customer)).thenReturn(customer);
        when(userMapper.toCustomerDTO(customer)).thenReturn(customerResponseDTO);

        CustomerResponseDTO result = authenticationService.registerCustomer(customerRequestDTO);

        assertNotNull(result);
        assertEquals("customer", result.getUsername());
        verify(userDAOService, times(1)).save(customer);
    }

    @Test
    void testLoginSuccess() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        GrantedAuthority authority = () -> "AUTHOR";

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jpaUserDetailsService.loadUserByUsername("author")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("author");
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwt-token");

        AuthenticationResponseDto response = authenticationService.login(authRequestDto);

        assertNotNull(response);
        assertEquals("author", response.getUsername());
        assertEquals("jwt-token", response.getJwt());
        assertTrue(response.getRole().contains("AUTHOR"));
    }

    @Test
    void testLoginFailure() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Bad credentials"));

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authenticationService.login(authRequestDto)
        );

        assertEquals("Invalid credentials", exception.getMessage());
    }
}
