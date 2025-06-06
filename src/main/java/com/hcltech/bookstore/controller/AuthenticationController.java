package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationRequestDto> register(@RequestBody AuthenticationRequestDto requestDto) {
        AuthenticationRequestDto registeredUser = authenticationService.register(requestDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        AuthenticationResponseDto response = authenticationService.login(requestDto);
        return ResponseEntity.ok(response);
    }
}
