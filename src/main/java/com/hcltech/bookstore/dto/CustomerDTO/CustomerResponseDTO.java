package com.hcltech.bookstore.dto.CustomerDTO;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerResponseDTO {
    private String username;
    private String name;
    private Set<String> roles;
}
