package com.hcltech.bookstore.controller;

import com.hcltech.bookstore.dto.purchased.PurchasedBookRequestDto;
import com.hcltech.bookstore.dto.purchased.PurchasedBookResponseDto;
import com.hcltech.bookstore.service.purchase.PurchasedBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchasedBookController {

    private final PurchasedBookService purchasedBookService;

    @PostMapping
    public ResponseEntity<PurchasedBookResponseDto> purchaseBook(@RequestBody @Valid PurchasedBookRequestDto dto) {
        return ResponseEntity.ok(purchasedBookService.purchaseBook(dto));
    }
}
