package com.hcltech.bookstore.dto.AuthenticationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String jwt;
    private String username;
    private Set<String> role;

}
