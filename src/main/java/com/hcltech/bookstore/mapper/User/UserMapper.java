package com.hcltech.bookstore.mapper.User;

import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerResponseDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface    UserMapper {

    Author toAuthor(AuthorRequestDTO dto);
    Customer toCustomer(CustomerRequestDTO dto);
    AuthorResponseDTO toAuthorDTO(Author author);
    CustomerResponseDTO toCustomerDTO(Customer customer);

}
