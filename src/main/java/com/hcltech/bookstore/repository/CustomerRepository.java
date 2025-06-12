package com.hcltech.bookstore.repository;

import com.hcltech.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByUsername(String username);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.purchasedBooks pb LEFT JOIN FETCH pb.book LEFT JOIN FETCH pb.customer WHERE c.id = :id")
    Optional<Customer> findByIdWithPurchases(@Param("id") Long id);

}
