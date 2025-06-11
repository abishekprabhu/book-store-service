package com.hcltech.bookstore.mapper.user;

import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface   UserMapper {
    Author toAuthor(AuthorRequestDto dto);
    Customer toCustomer(CustomerRequestDto dto);
    AuthorResponseDto toAuthorDTO(Author author);
    CustomerResponseDto toCustomerDTO(Customer customer);

}
