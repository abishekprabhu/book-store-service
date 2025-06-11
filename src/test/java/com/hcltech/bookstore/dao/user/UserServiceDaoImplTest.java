package com.hcltech.bookstore.dao.user;

import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.User;
import com.hcltech.bookstore.repository.AuthorRepository;
import com.hcltech.bookstore.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceDaoImplTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UserServiceDaoImpl userDAOService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Author() {
        Author author = new Author();
        when(authorRepository.save(author)).thenReturn(author);

        Author result = userDAOService.save(author);

        assertSame(author, result);
        verify(authorRepository).save(author);
        verifyNoInteractions(customerRepository);
    }

    @Test
    void save_Customer() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = userDAOService.save(customer);

        assertSame(customer, result);
        verify(customerRepository).save(customer);
        verifyNoInteractions(authorRepository);
    }

    @Test
    void save_UnsupportedType() {
        User user = mock(User.class);
        assertThrows(IllegalArgumentException.class, () -> userDAOService.save(user));
    }

    @Test
    void findByUsername_AuthorFound() {
        Author author = new Author();
        when(authorRepository.findByUsername("user1")).thenReturn(Optional.of(author));

        User result = userDAOService.findByUsername("user1");

        assertSame(author, result);
        verify(authorRepository).findByUsername("user1");
        verifyNoInteractions(customerRepository);
    }

    @Test
    void findByUsername_CustomerFound() {
        when(authorRepository.findByUsername("user2")).thenReturn(Optional.empty());
        Customer customer = new Customer();
        when(customerRepository.findByUsername("user2")).thenReturn(Optional.of(customer));

        User result = userDAOService.findByUsername("user2");

        assertSame(customer, result);
        verify(authorRepository).findByUsername("user2");
        verify(customerRepository).findByUsername("user2");
    }

    @Test
    void findByUsername_NotFound() {
        when(authorRepository.findByUsername("user3")).thenReturn(Optional.empty());
        when(customerRepository.findByUsername("user3")).thenReturn(Optional.empty());

        User result = userDAOService.findByUsername("user3");

        assertNull(result);
        verify(authorRepository).findByUsername("user3");
        verify(customerRepository).findByUsername("user3");
    }
}