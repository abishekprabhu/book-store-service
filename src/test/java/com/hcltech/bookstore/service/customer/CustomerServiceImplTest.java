package com.hcltech.bookstore.service.customer;

import com.hcltech.bookstore.dao.customer.CustomerServiceDao;
import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.mapper.customer.CustomerMapper;
import com.hcltech.bookstore.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerServiceDao customerServiceDao;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    void testGetAllCustomers() {
        Customer customer = new Customer();
        customer.setUsername("user1");
        customer.setName("User One");

        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setUsername("user1");
        responseDto.setName("User One");

        when(customerServiceDao.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDtoList(List.of(customer))).thenReturn(List.of(responseDto));

        List<CustomerResponseDto> customers = customerService.getAll();

        assertEquals(1, customers.size());
        assertEquals("user1", customers.get(0).getUsername());
    }

    @Test
    void testGetCustomerById_Found() {
        Customer customer = new Customer();
        customer.setUsername("user1");
        customer.setName("User One");

        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setUsername("user1");
        responseDto.setName("User One");

        when(customerServiceDao.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDto(customer)).thenReturn(responseDto);

        CustomerResponseDto result = customerService.getById(1L);

        assertEquals("user1", result.getUsername());
        assertEquals("User One", result.getName());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerServiceDao.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            customerService.getById(1L);
        });

        assertEquals("Customer not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateCustomer() {
        CustomerRequestDto request = new CustomerRequestDto();
        request.setUsername("user1");
        request.setPassword("Password@123");
        request.setName("Updated User");

        Customer customer = new Customer();
        customer.setUsername("user1");
        customer.setPassword("Password@123");
        customer.setName("User One");

        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setUsername("user1");
        responseDto.setName("Updated User");

        when(customerServiceDao.findById(1L)).thenReturn(Optional.of(customer));
        when(customerServiceDao.save(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toDto(customer)).thenReturn(responseDto);

        CustomerResponseDto response = customerService.update(1L, request);

        assertEquals("user1", response.getUsername());
        assertEquals("Updated User", response.getName());
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerServiceDao).deleteById(1L);

        assertDoesNotThrow(() -> customerService.delete(1L));
        verify(customerServiceDao, times(1)).deleteById(1L);
    }
}
