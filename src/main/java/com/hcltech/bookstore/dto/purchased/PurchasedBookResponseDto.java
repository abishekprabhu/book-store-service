package com.hcltech.bookstore.dto.purchased;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchasedBookResponseDto {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long customerId;
    private String customerName;
    private int quantity;
    private LocalDateTime purchaseDate;
}

