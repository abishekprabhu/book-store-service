package com.hcltech.bookstore.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.awt.print.Book;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author extends User{

    private String biography;

/*    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;*/
}
