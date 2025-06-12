package com.hcltech.bookstore.dto.customer;

import com.hcltech.bookstore.dto.purchased.PurchasedBookResponseDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CustomerResponseDto {
    private String username;
    private String name;
    private Set<String> roles;
    private List<PurchasedBookResponseDto> purchasedBooks;
}
