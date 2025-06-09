package com.hcltech.bookstore.mapper.purchase;

import com.hcltech.bookstore.dto.PurchasedBookDTO.PurchasedBookResponseDTO;
import com.hcltech.bookstore.model.PurchasedBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PurchasedBookMapper {

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    PurchasedBookResponseDTO toDTO(PurchasedBook purchasedBook);
}
