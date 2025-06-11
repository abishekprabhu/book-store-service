package com.hcltech.bookstore.service.authentication;

import com.hcltech.bookstore.dao.user.UserServiceDao;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.mapper.user.UserMapper;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private JpaUserDetailsService jpaUserDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserServiceDao userServiceDao;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder; // Add this line

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterAuthor() {
        AuthorRequestDto dto = new AuthorRequestDto();
        dto.setUsername("author");
        dto.setPassword("Author@28");
        dto.setName("Author Name");
        dto.setBiography("Bio");

        Author author = new Author();
        author.setUsername("author");

        AuthorResponseDto responseDTO = new AuthorResponseDto();
        responseDTO.setUsername("author");

        when(userMapper.toAuthor(dto)).thenReturn(author);
        when(passwordEncoder.encode("Author@28")).thenReturn("encodedPassword"); // Mock encoding
        when(userServiceDao.save(author)).thenReturn(author);
        when(userMapper.toAuthorDTO(author)).thenReturn(responseDTO);

        AuthorResponseDto result = authenticationService.registerAuthor(dto);

        assertEquals("author", result.getUsername());
        verify(userServiceDao).save(author);
    }

    @Test
    void testRegisterCustomer() {
        CustomerRequestDto dto = new CustomerRequestDto();
        dto.setUsername("customer");
        dto.setPassword("Password@28");
        dto.setName("Customer Name");

        Customer customer = new Customer();
        customer.setUsername("customer");

        CustomerResponseDto responseDTO = new CustomerResponseDto();
        responseDTO.setUsername("customer");

        when(userMapper.toCustomer(dto)).thenReturn(customer);
        when(passwordEncoder.encode("Password@28")).thenReturn("encodedPassword"); // Mock encoding
        when(userServiceDao.save(customer)).thenReturn(customer);
        when(userMapper.toCustomerDTO(customer)).thenReturn(responseDTO);

        CustomerResponseDto result = authenticationService.registerCustomer(dto);

        assertEquals("customer", result.getUsername());
        verify(userServiceDao).save(customer);
    }

    @Test
    void testLoginSuccess() {
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setUsername("user");
        dto.setPassword("pass");

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jpaUserDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user");
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwt-token");
        when(userDetails.getAuthorities())
                .thenReturn((Collection) Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));        AuthenticationResponseDto response = authenticationService.login(dto);

        assertEquals("user", response.getUsername());
        assertEquals("jwt-token", response.getJwt());
        assertTrue(response.getRole().contains("ROLE_USER"));
    }

    @Test
    void testLoginFailure() {
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setUsername("user");
        dto.setPassword("wrong");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new RuntimeException("Bad credentials"));

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.login(dto));
    }
}
