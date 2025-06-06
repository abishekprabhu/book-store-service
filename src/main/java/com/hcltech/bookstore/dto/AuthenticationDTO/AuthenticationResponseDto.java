package com.hcltech.bookstore.dto.AuthenticationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDto {
    private String jwt;
}
