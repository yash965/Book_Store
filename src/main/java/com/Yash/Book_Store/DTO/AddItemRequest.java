package com.Yash.Book_Store.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemRequest {
    private Long bookId;
    private int quantity;
}
