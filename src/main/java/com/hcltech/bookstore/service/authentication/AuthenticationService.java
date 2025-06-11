package com.hcltech.bookstore.service.authentication;

import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;

public interface AuthenticationService {

    AuthorResponseDto registerAuthor(final AuthorRequestDto dto) ;
    CustomerResponseDto registerCustomer(final CustomerRequestDto dto);
    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) ;
}
