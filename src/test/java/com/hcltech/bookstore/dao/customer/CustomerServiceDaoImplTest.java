package com.hcltech.bookstore.dao.customer;

import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceDaoImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceDaoImpl customerServiceDAO;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Jane Doe");
    }

    @Test
    void testFindById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Optional<Customer> result = customerServiceDAO.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Jane Doe", result.get().getName());
    }

    @Test
    void testFindAll() {
        List<Customer> customers = Arrays.asList(customer, new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerServiceDAO.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer saved = customerServiceDAO.save(customer);
        assertNotNull(saved);
        assertEquals("Jane Doe", saved.getName());
    }

}