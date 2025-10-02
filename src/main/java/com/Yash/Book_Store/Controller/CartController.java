package com.Yash.Book_Store.Controller;

import DTO.AddItemRequest;
import DTO.CartDto;
import com.Yash.Book_Store.Service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService)
    {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCurrentUserCart()
    {
        return new ResponseEntity<>(cartService.getCartDtoForCurrentUser(), HttpStatus.OK);
    }

    @GetMapping("jwtTesting")
    public ResponseEntity<String> checkJwt()
    {
        return new ResponseEntity<>("JWT checked", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CartDto> addBookToCart(@RequestBody AddItemRequest addItemRequest)
    {
        CartDto cart = cartService.addBookToCart(addItemRequest.getBookId(), addItemRequest.getQuantity());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<CartDto> removeBookFromCart(@PathVariable Long bookId)
    {
        CartDto cart = cartService.removeBookFromCart(bookId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
