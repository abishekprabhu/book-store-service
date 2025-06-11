package com.hcltech.bookstore.mapper.author;

import com.hcltech.bookstore.dto.author.AuthorResponseDto;
import com.hcltech.bookstore.dto.book.BookSummaryDto;
import com.hcltech.bookstore.model.Author;
import com.hcltech.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Mapping(target = "books", source = "books")
    AuthorResponseDto toDTO(Author author);

    @Mapping(target = "imageUrl", expression = "java(toBase64Image(book.getImg()))")
    BookSummaryDto toBookSummary(Book book);

    default String toBase64Image(byte[] img) {
        if (img == null) return null;
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(img);
    }
}
