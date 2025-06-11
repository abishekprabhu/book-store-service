
package com.hcltech.bookstore.mapper.book;

import com.hcltech.bookstore.exception.CustomException;
import com.hcltech.bookstore.dto.book.BookRequestDto;
import com.hcltech.bookstore.dto.book.BookResponseDto;
import com.hcltech.bookstore.model.Book;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "imageUrl", expression = "java(toBase64Image(book.getImg()))")
    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.name", target = "authorName")
    BookResponseDto toDTO(Book book);

    @Mapping(target = "img", source = "img", qualifiedByName = "multipartToBytes")
//    @Mapping(source = "authorId", target = "author.id")
    Book toEntity(BookRequestDto bookDTO);

    @Named("multipartToBytes")
    static byte[] multipartToBytes(MultipartFile file) {
        try {
            return file != null ? file.getBytes() : null;
        } catch (IOException e) {
            throw new CustomException("Failed to convert image" + e.getMessage());
        }
    }

    default String toBase64Image(byte[] img) {
        if (img == null) return null;
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(img);
    }
}