package com.hcltech.bookstore.service.purchase;

import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookRequestDTO;
import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookResponseDTO;

public interface PurchasedBookService {
    PurchasedBookResponseDTO purchaseBook(PurchasedBookRequestDTO dto);
}
