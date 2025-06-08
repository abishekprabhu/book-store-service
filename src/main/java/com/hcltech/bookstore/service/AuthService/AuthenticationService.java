package com.hcltech.bookstore.service.AuthService;

import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerResponseDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;

public interface AuthenticationService {

    AuthorResponseDTO registerAuthor(final AuthorRequestDTO dto) ;
    CustomerResponseDTO registerCustomer(final CustomerRequestDTO dto);
    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) ;
}
