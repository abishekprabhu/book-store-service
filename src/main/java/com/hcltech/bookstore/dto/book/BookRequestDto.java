package com.hcltech.bookstore.dto.book;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestDto {

        @NotBlank(message = "Title is required")
        private String title;

        @NotBlank(message = "ISBN is required")
        private String isbn;

        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 8, fraction = 2, message = "Invalid price format")
        private double price;

        @Size(max = 1000, message = "Description too long")
        private String description;

        @PositiveOrZero(message = "Stock must be 0 or more")
        private int stock;

        private Long authorId;

        @Nullable
        private MultipartFile img;

}
