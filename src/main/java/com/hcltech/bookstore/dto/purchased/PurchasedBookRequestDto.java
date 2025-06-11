package com.hcltech.bookstore.dto.purchased;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchasedBookRequestDto {
    @NotNull
    private Long bookId;

    @NotNull
    private Long customerId;

    @Min(1)
    private int quantity;
}
