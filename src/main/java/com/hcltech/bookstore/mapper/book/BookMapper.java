package com.hcltech.bookstore.mapper.book;

import com.hcltech.bookstore.dto.BookDTO.BookRequestDTO;
import com.hcltech.bookstore.dto.BookDTO.BookResponseDTO;
import com.hcltech.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "returnedImg", source = "img")
    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "author.name", target = "authorName") // optional if you want to show name in responses
    BookResponseDTO toDTO(Book book);

    @Mapping(target = "img", source = "img", qualifiedByName = "multipartToBytes")
    @Mapping(source = "authorId", target = "author.id")
    Book toEntity(BookRequestDTO bookDTO);

    @Named("multipartToBytes")
    static byte[] mapMultipartToBytes(MultipartFile file) {
        try {
            return file != null ? file.getBytes() : null;
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert image", e);
        }
    }
}
