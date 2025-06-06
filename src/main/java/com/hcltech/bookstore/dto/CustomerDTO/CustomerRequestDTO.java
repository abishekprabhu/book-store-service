package com.hcltech.bookstore.dto.CustomerDTO;

import lombok.Data;

import java.util.Set;

@Data
public class CustomerRequestDTO {

    private String username;
    private String password;
    private String name;
    private Set<String> roles;

}

