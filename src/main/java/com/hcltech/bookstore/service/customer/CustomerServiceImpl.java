package com.hcltech.bookstore.service.customer;

import com.hcltech.bookstore.dao.customer.CustomerServiceDao;
import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.mapper.customer.CustomerMapper;
import com.hcltech.bookstore.model.Customer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerServiceDao customerServiceDAO;
    private final PasswordEncoder passwordEncoder;
    private final CustomerMapper customerMapper;

    private static final String CUSTOMER_NOT_FOUND = "Customer not found with id: ";

    @Override
    @Transactional
    public CustomerResponseDto getById(Long id) {
        log.info("Fetching customer with ID: {}", id);
        Customer customer = customerServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOT_FOUND + id));
        log.debug("Customer found: {}", customer.getUsername());
        return customerMapper.toDto(customer);
    }
    @Override
    @Transactional
    public List<CustomerResponseDto> getAll() {
        log.info("Fetching all customers");
        List<Customer> customers = customerServiceDAO.findAll();
        log.debug("Total customers retrieved: {}", customers.size());
        return customerMapper.toDtoList(customers);
    }
    @Override
    @Transactional
    public CustomerResponseDto update(Long id, CustomerRequestDto dto) {
        log.info("Updating customer with ID: {}", id);
        Customer customer = customerServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOT_FOUND + id));
        customer.setName(dto.getName());
        Customer updatedCustomer = customerServiceDAO.save(customer);
        log.info("Customer with ID {} updated successfully", id);
        return customerMapper.toDto(updatedCustomer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting customer with ID: {}", id);
        customerServiceDAO.deleteById(id);
        log.info("Customer with ID {} deleted successfully", id);
    }
}
