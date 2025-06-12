package com.hcltech.bookstore.service.customer;

import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto getById(Long id);
    List<CustomerResponseDto> getAll();
    CustomerResponseDto update(Long id, CustomerRequestDto dto);
    void delete(Long id);
}
