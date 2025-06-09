package com.hcltech.bookstore.dao.customerDao;

import com.hcltech.bookstore.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceDAO {
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    void delete(Customer customer);
    List<Customer> findAll();
}
