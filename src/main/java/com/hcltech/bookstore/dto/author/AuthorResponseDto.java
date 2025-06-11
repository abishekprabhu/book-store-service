package com.hcltech.bookstore.dto.author;

import com.hcltech.bookstore.dto.book.BookSummaryDto;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class AuthorResponseDto {
    private String username;
    private String name;
    private String biography;
    private Set<String> roles;
    //for get author purpose
    private List<BookSummaryDto> books;

}
