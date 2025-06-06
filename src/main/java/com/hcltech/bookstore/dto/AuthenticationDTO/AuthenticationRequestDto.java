package com.hcltech.bookstore.dto.AuthenticationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthenticationRequestDto {

    private String username;
    private String password;
    private String name;
    private String roles;

}
