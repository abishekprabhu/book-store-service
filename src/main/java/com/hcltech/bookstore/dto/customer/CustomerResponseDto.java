package com.hcltech.bookstore.dto.customer;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerResponseDto {
    private String username;
    private String name;
    private Set<String> roles;
}
