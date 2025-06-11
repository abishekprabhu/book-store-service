package com.hcltech.bookstore.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Long id;
    private String title;
    private String isbn;
    private double price;
    private int stock;
    private String description;
    private Long authorId;
    private String authorName;
    private String imageUrl;
}
