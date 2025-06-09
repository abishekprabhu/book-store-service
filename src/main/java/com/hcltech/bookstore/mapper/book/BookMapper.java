
package com.hcltech.bookstore.mapper.book;

import com.hcltech.bookstore.Exception.CustomException;
import com.hcltech.bookstore.dto.BookDTO.BookRequestDTO;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;
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
    BookResponseDTO toDTO(Book book);

    @Mapping(target = "img", source = "img", qualifiedByName = "multipartToBytes")
//    @Mapping(source = "authorId", target = "author.id")
    Book toEntity(BookRequestDTO bookDTO);

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