package com.hcltech.bookstore.dto.AuthorDTO;

import com.hcltech.bookstore.dto.BookDTO.BookSummaryDTO;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AuthorResponseDTO {
    private String username;
    private String name;
    private String biography;
    private Set<String> roles;
    //for get author purpose
    private List<BookSummaryDTO> books;

}
