package com.hcltech.bookstore.dto.BookDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private String isbn;
    private double price;
    private String description;
    private Long authorId;
    private String authorName;
    private byte[] returnedImg;
}
