package com.hcltech.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcltech.bookstore.config.SecurityTestConfig;
import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookRequestDTO;
import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookResponseDTO;
import com.hcltech.bookstore.service.purchase.PurchasedBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PurchasedBookController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {PurchasedBookController.class, SecurityTestConfig.class})
class PurchasedBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchasedBookService purchasedBookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPurchaseBook_Success() throws Exception {
        PurchasedBookRequestDTO request = new PurchasedBookRequestDTO();
        request.setBookId(1L);
        request.setCustomerId(2L);
        request.setQuantity(3);

        PurchasedBookResponseDTO response = new PurchasedBookResponseDTO();
        response.setId(10L);
        response.setBookId(1L);
        response.setBookTitle("Book Title");
        response.setCustomerId(2L);
        response.setCustomerName("Customer Name");
        response.setQuantity(3);
        response.setPurchaseDate(LocalDateTime.now());

        when(purchasedBookService.purchaseBook(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.bookId").value(1L))
                .andExpect(jsonPath("$.customerId").value(2L))
                .andExpect(jsonPath("$.quantity").value(3));
    }

    @Test
    void testPurchaseBook_ValidationError() throws Exception {
        PurchasedBookRequestDTO request = new PurchasedBookRequestDTO();
        // Missing bookId, customerId, and quantity = 0 (invalid)

        request.setQuantity(0);

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPurchaseBook_BookNotFound() throws Exception {
        PurchasedBookRequestDTO request = new PurchasedBookRequestDTO();
        request.setBookId(99L);
        request.setCustomerId(2L);
        request.setQuantity(1);

        when(purchasedBookService.purchaseBook(any()))
                .thenThrow(new RuntimeException("Book not found"));

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testPurchaseBook_InsufficientStock() throws Exception {
        PurchasedBookRequestDTO request = new PurchasedBookRequestDTO();
        request.setBookId(1L);
        request.setCustomerId(2L);
        request.setQuantity(100);

        when(purchasedBookService.purchaseBook(any()))
                .thenThrow(new IllegalStateException("Not enough stock available"));

        mockMvc.perform(post("/api/v1/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
