package com.hcltech.bookstore.mapper.User;

import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Mapper(componentModel = "spring")
public interface    UserMapper {
    @Mapping(source = "roles", target = "roles", qualifiedByName = "stringToSet")
    Author toAuthor(AuthorRequestDTO dto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "stringToSet")
    Customer toCustomer(CustomerRequestDTO dto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "setToString")
    AuthorRequestDTO toAuthorDTO(Author author);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "setToString")
    CustomerRequestDTO toCustomerDTO(Customer customer);

    @Named("stringToSet")
    static Set<String> stringToSet(String roles) {
        return roles == null || roles.isBlank() ? Set.of() : Set.of(roles.split(","));
    }

    @Named("setToString")
    static String setToString(Set<String> roles) {
        return roles == null || roles.isEmpty() ? "" : String.join(",", roles);
    }
/*    @Mapping(source = "roles", target = "roles", qualifiedByName = "stringToSet")
    Author toAuthor(AuthenticationRequestDto dto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "stringToSet")
    Customer toCustomer(AuthenticationRequestDto dto);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "setToString")
    AuthenticationRequestDto toAuthenticationRequestDto(User user);*/

//    @Named("stringToSet")
//    static Set<String> mapRoles(String roles) {
//        return roles == null || roles.isBlank()
//                ? new HashSet<>()
//                : new HashSet<>(Arrays.asList(roles.split(",")));
//    }
//
//    @Named("setToString")
//    static String mapRoles(Set<String> roles) {
//        return roles == null || roles.isEmpty()
//                ? ""
//             : String.join(",", roles);
//    }
}
