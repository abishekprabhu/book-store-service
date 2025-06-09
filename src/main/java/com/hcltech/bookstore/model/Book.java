package com.hcltech.bookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
//    @Column(unique = true, nullable = false)
    private String isbn;

//    @Column(precision = 10, scale = 2)
    private double price;
    private String description;

    @Lob
//    @Column(columnDefinition = "LONGBLOB")
    private byte[] img;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    @OneToMany(mappedBy = "book")
    @JsonBackReference
    private List<PurchasedBook> purchasedBooks;

}
