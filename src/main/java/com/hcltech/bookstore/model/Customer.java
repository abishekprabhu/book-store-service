package com.hcltech.bookstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends User{

    private String name;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<PurchasedBook> purchasedBooks;
}
