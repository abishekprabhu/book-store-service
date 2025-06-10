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
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

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
    private UserDAOService userDAOService;

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
        AuthorRequestDTO dto = new AuthorRequestDTO();
        dto.setUsername("author");
        dto.setPassword("Author@28");
        dto.setName("Author Name");
        dto.setBiography("Bio");

        Author author = new Author();
        author.setUsername("author");

        AuthorResponseDTO responseDTO = new AuthorResponseDTO();
        responseDTO.setUsername("author");

        when(userMapper.toAuthor(dto)).thenReturn(author);
        when(passwordEncoder.encode("Author@28")).thenReturn("encodedPassword"); // Mock encoding
        when(userDAOService.save(author)).thenReturn(author);
        when(userMapper.toAuthorDTO(author)).thenReturn(responseDTO);

        AuthorResponseDTO result = authenticationService.registerAuthor(dto);

        assertEquals("author", result.getUsername());
        verify(userDAOService).save(author);
    }

    @Test
    void testRegisterCustomer() {
        CustomerRequestDTO dto = new CustomerRequestDTO();
        dto.setUsername("customer");
        dto.setPassword("Password@28");
        dto.setName("Customer Name");

        Customer customer = new Customer();
        customer.setUsername("customer");

        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        responseDTO.setUsername("customer");

        when(userMapper.toCustomer(dto)).thenReturn(customer);
        when(passwordEncoder.encode("Password@28")).thenReturn("encodedPassword"); // Mock encoding
        when(userDAOService.save(customer)).thenReturn(customer);
        when(userMapper.toCustomerDTO(customer)).thenReturn(responseDTO);

        CustomerResponseDTO result = authenticationService.registerCustomer(dto);

        assertEquals("customer", result.getUsername());
        verify(userDAOService).save(customer);
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
