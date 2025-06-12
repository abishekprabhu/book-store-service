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
        Customer customer = customerServiceDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CUSTOMER_NOT_FOUND + id));

        return customerMapper.toDto(customer);
    }
    @Override
    @Transactional
    public List<CustomerResponseDto> getAll() {
        return customerMapper.toDtoList(customerServiceDAO.findAll());
    }
    @Override
    @Transactional
    public CustomerResponseDto update(Long id, CustomerRequestDto dto) {
        Customer customer = customerServiceDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        customer.setName(dto.getName());
/*        customer.setUsername(dto.getUsername());
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));*/
        return customerMapper.toDto(customerServiceDAO.save(customer));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        customerServiceDAO.deleteById(id);
    }
}
