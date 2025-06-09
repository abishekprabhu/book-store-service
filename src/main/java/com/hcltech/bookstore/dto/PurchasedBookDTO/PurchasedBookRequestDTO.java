package com.hcltech.bookstore.dto.PurchasedBookDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchasedBookRequestDTO {
    @NotNull
    private Long bookId;

    @NotNull
    private Long customerId;

    @Min(1)
    private int quantity;
}
