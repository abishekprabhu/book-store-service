package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.authentication.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.author.AuthorRequestDto;
import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.service.authentication.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register/author")
    public ResponseEntity<AuthorResponseDto> registerAuthor(@Valid @RequestBody AuthorRequestDto dto) {
        return ResponseEntity.ok(authenticationService.registerAuthor(dto));
    }

    @PostMapping("/register/customer")
    public ResponseEntity<CustomerResponseDto> registerCustomer(@Valid @RequestBody CustomerRequestDto dto) {
        return ResponseEntity.ok(authenticationService.registerCustomer(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto dto) {
        return ResponseEntity.ok(authenticationService.login(dto));
    }

}
