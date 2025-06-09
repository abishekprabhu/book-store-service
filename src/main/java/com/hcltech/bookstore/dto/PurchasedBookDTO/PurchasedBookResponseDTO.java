package com.hcltech.bookstore.dto.PurchasedBookDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchasedBookResponseDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long customerId;
    private String customerName;
    private int quantity;
    private LocalDateTime purchaseDate;
}

