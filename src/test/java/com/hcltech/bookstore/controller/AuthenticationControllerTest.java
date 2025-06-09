package com.hcltech.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationRequestDto;
import com.hcltech.bookstore.dto.AuthenticationDTO.AuthenticationResponseDto;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorRequestDTO;
import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerRequestDTO;
import com.hcltech.bookstore.dto.CustomerDTO.CustomerResponseDTO;
import com.hcltech.bookstore.service.AuthService.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthorRequestDTO authorRequestDTO;
    private AuthorResponseDTO authorResponseDTO;
    private CustomerRequestDTO customerRequestDTO;
    private CustomerResponseDTO customerResponseDTO;
    private AuthenticationRequestDto authRequestDto;
    private AuthenticationResponseDto authResponseDto;

    @BeforeEach
    void setUp() {
        authorRequestDTO = new AuthorRequestDTO();
        authorRequestDTO.setUsername("author");
        authorRequestDTO.setPassword("password");

        authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setUsername("author");

        customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setUsername("customer");
        customerRequestDTO.setPassword("password");

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setUsername("customer");

        authRequestDto = new AuthenticationRequestDto();
        authRequestDto.setUsername("author");
        authRequestDto.setPassword("password");

        authResponseDto = new AuthenticationResponseDto("author", "jwt-token", Set.of("AUTHOR"));
    }

    @Test
    void testRegisterAuthor() throws Exception {
        when(authenticationService.registerAuthor(authorRequestDTO)).thenReturn(authorResponseDTO);

        mockMvc.perform(post("/api/v1/auth/register/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("author"));
    }

    @Test
    void testRegisterCustomer() throws Exception {
        when(authenticationService.registerCustomer(customerRequestDTO)).thenReturn(customerResponseDTO);

        mockMvc.perform(post("/api/v1/auth/register/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("customer"));
    }

    @Test
    void testLogin() throws Exception {
        when(authenticationService.login(authRequestDto)).thenReturn(authResponseDto);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("author"))
                .andExpect(jsonPath("$.jwt").value("jwt-token"))
                .andExpect(jsonPath("$.role").isArray());
    }
}
