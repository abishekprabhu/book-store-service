package com.hcltech.bookstore.dao.customer;

import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceDaoImpl implements CustomerServiceDao {

    private final CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }
/*    @Override
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }*/
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

}
