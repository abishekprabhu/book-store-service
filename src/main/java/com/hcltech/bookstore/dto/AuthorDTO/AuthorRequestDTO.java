package com.hcltech.bookstore.dto.AuthorDTO;

import lombok.Data;

import java.util.Set;

@Data
public class AuthorRequestDTO {
    private String username;
    private String password;
    private String name;
    private String biography;
    private Set<String> roles;
}
