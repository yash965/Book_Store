package com.Yash.Book_Store.Controller;

import DTO.AddItemRequest;
import com.Yash.Book_Store.Entity.Cart;
import com.Yash.Book_Store.Entity.CartItem;
import com.Yash.Book_Store.Service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCurrentUserCart()
    {
        return new ResponseEntity<>(cartService.getCartForCurrentUser(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cart> addBookToCar(@RequestBody AddItemRequest addItemRequest)
    {
        Cart cart = cartService.getCartForCurrentUser();
        cart = cartService.addBookToCart(addItemRequest.getBookId(), addItemRequest.getQuantity());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Cart> removeBookFromCart(@PathVariable Long bookId)
    {
        Cart cart = cartService.removeBookFromCart(bookId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
