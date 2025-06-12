package com.hcltech.bookstore.mapper.customer;

import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.mapper.purchase.PurchasedBookMapper;
import com.hcltech.bookstore.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PurchasedBookMapper.class)
public interface CustomerMapper {
    Customer toEntity(CustomerRequestDto dto);


    @Mapping(source = "purchasedBooks", target = "purchasedBooks")
    CustomerResponseDto toDto(Customer customer);

    List<CustomerResponseDto> toDtoList(List<Customer> customers);
}
