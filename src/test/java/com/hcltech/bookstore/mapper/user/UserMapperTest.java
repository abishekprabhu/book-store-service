package com.hcltech.bookstore.mapper.user;

import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToAuthor() {
        AuthorRequestDto dto = new AuthorRequestDto();
        dto.setUsername("authorUser");
        dto.setPassword("Password@123");
        dto.setName("Author Name");
        dto.setBiography("Author bio");

        Author author = userMapper.toAuthor(dto);

        assertEquals("authorUser", author.getUsername());
        assertEquals("Password@123", author.getPassword());
        assertEquals("Author Name", author.getName());
        assertEquals("Author bio", author.getBiography());
    }

    @Test
    void testToCustomer() {
        CustomerRequestDto dto = new CustomerRequestDto();
        dto.setUsername("customerUser");
        dto.setPassword("Password@123");
        dto.setName("Customer Name");

        Customer customer = userMapper.toCustomer(dto);

        assertEquals("customerUser", customer.getUsername());
        assertEquals("Customer Name", customer.getName());
    }

    @Test
    void testToAuthorDTO() {
        Author author = new Author();
        author.setUsername("authorUser");
        author.setName("Author Name");
        author.setBiography("Bio");
        author.setRoles(Set.of("AUTHOR"));

        AuthorResponseDto dto = userMapper.toAuthorDTO(author);

        assertEquals("authorUser", dto.getUsername());
        assertEquals("Author Name", dto.getName());
        assertEquals("Bio", dto.getBiography());
        assertTrue(dto.getRoles().contains("AUTHOR"));
    }

    @Test
    void testToCustomerDTO() {
        Customer customer = new Customer();
        customer.setUsername("customerUser");
        customer.setName("Customer Name");
        customer.setRoles(Set.of("CUSTOMER"));

        CustomerResponseDto dto = userMapper.toCustomerDTO(customer);

        assertEquals("customerUser", dto.getUsername());
        assertEquals("Customer Name", dto.getName());
        assertTrue(dto.getRoles().contains("CUSTOMER"));
    }
}