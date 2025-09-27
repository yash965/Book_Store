package com.Yash.Book_Store.Entity;

import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;

@Entity
@Setter
@Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // This means Many cartItem can reference a single book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntry book;

    // This means many cartItems can be referred to one cart.
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    private int quantity;
}
