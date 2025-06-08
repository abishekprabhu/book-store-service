package com.hcltech.bookstore.mapper.User;

import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerResponseDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;


@Mapper(componentModel = "spring")
public interface    UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Author toAuthor(AuthorRequestDTO dto);
    Customer toCustomer(CustomerRequestDTO dto);
    AuthorResponseDTO toAuthorDTO(Author author);
    CustomerResponseDTO toCustomerDTO(Customer customer);

/*    @Named("stringToSet")
    static Set<String> stringToSet(String roles) {
        return roles == null || roles.isBlank() ? Set.of() : Set.of(roles.split(","));
    }

    @Named("setToString")
    static String setToString(Set<String> roles) {
        return roles == null || roles.isEmpty() ? "" : String.join(",", roles);
    }*/
}
