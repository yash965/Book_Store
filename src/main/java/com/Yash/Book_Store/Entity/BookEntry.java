package com.Yash.Book_Store.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class BookEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String bookName;

    @Column(name = "category", nullable = true, length = 100)
    private String category;

    @Column(name = "author", nullable = false, length = 100)
    private String author;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "date", nullable = true)
    private LocalDateTime date;

    @Column(name = "image", nullable = false)
    String image;

    @Column(name = "description", nullable = false)
    String description;
}
