package com.hcltech.bookstore.mapper.author;

import com.hcltech.bookstore.dto.AuthorDTO.AuthorResponseDTO;
import com.hcltech.bookstore.dto.BookDTO.BookSummaryDTO;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "books", source = "books")
    AuthorResponseDTO toDTO(Author author);

    @Mapping(target = "imageUrl", expression = "java(toBase64Image(book.getImg()))")
    BookSummaryDTO toBookSummary(Book book);

    default String toBase64Image(byte[] img) {
        if (img == null) return null;
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(img);
    }
}
