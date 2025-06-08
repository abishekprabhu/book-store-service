package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerResponseDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Customer;
import com.hcltech.bookstore.service.AuthService.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/author")
    public ResponseEntity<AuthorResponseDTO> registerAuthor(@RequestBody AuthorRequestDTO dto) {
        return ResponseEntity.ok(authenticationService.registerAuthor(dto));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.ok(authenticationService.registerCustomer(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto dto) {
        return ResponseEntity.ok(authenticationService.login(dto));
    }

}
