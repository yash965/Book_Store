package com.Yash.Book_Store.DTO;

import com.Yash.Book_Store.Entity.BookEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private BookEntry book;
    private int quantity;
    private double priceAtPurchase;
}
