package com.hcltech.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcltech.bookstore.dto.customer.CustomerRequestDto;
import com.hcltech.bookstore.dto.customer.CustomerResponseDto;
import com.hcltech.bookstore.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes ={ CustomerController.class , SecurityException.class })
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CustomerService customerService;
    @Autowired
    private ObjectMapper objectMapper;
    private CustomerRequestDto requestDto;
    private CustomerResponseDto responseDto;
    @BeforeEach
    void setUp() {
        requestDto = new CustomerRequestDto();
        requestDto.setUsername("john_doe");
        requestDto.setPassword("password123");
        requestDto.setName("John Doe");
        responseDto = new CustomerResponseDto();
        responseDto.setUsername("john_doe");
        responseDto.setName("John Doe");
        responseDto.setRoles(Set.of("ROLE_CUSTOMER"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        when(customerService.getById(1L)).thenReturn(responseDto);
        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
    @Test
    void testGetAllCustomers() throws Exception {
        when(customerService.getAll()).thenReturn(List.of(responseDto));
        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        com.hcltech.bookstore.dto.customer.CustomerRequestDto request = new com.hcltech.bookstore.dto.customer.CustomerRequestDto();
        request.setUsername("user112");
        request.setPassword("Password@123");
        request.setName("Updated User");

        CustomerResponseDto response = new CustomerResponseDto();
        response.setUsername("user112");
        response.setName("Updated User");

        when(customerService.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user112"))
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(customerService).delete(1L);

        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isNoContent());
    }

}