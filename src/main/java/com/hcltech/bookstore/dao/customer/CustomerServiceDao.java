package com.hcltech.bookstore.dao.customer;

import com.hcltech.bookstore.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceDao {
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    void delete(Customer customer);
    List<Customer> findAll();
}
