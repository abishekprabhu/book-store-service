package com.hcltech.bookstore.service.purchase;

import com.hcltech.bookstore.dto.purchased.PurchasedBookRequestDto;
import com.hcltech.bookstore.dto.purchased.PurchasedBookResponseDto;

public interface PurchasedBookService {
    PurchasedBookResponseDto purchaseBook(PurchasedBookRequestDto dto);
}
