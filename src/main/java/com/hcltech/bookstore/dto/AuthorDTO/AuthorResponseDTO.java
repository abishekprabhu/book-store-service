package com.hcltech.bookstore.dto.AuthorDTO;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AuthorResponseDTO {
    private String username;
    private String name;
    private String biography;
    private Set<String> roles;
}
