package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.Entity.BookEntry;
import com.Yash.Book_Store.Entity.Cart;
import com.Yash.Book_Store.Entity.CartItem;
import com.Yash.Book_Store.Entity.User;
import com.Yash.Book_Store.Repository.BookStore_Repository;
import com.Yash.Book_Store.Repository.CartRepository;
import com.Yash.Book_Store.Repository.User_Repository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final BookStore_Repository bookStoreRepository;
    private final User_Repository userRepository;

    // Constructor Injection
    public CartService(CartRepository cartRepository, BookStore_Repository bookStoreRepository, User_Repository userRepository)
    {
        this.cartRepository = cartRepository;
        this.bookStoreRepository = bookStoreRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Authenticated user not found"));
    }

    @Transactional
    public Cart getCartForCurrentUser()
    {
        User user = getCurrentUser();

        return cartRepository.findByUser(user).orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
        });
    }

    @Transactional
    public Cart addBookToCart(Long bookId, int quantity)
    {
        Cart cart = getCartForCurrentUser();
        BookEntry book = bookStoreRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        for(CartItem cartItem : cart.getItems())
        {
            if(cartItem.getId().equals(bookId))
            {
                cartItem.setQuantity(cartItem.getQuantity()+quantity);
                return cartRepository.save(cart);
            }
        }

        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(quantity);
        item.setCart(cart);
        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeBookFromCart(Long bookId) {
        Cart cart = getCartForCurrentUser();
        cart.getItems().removeIf(item -> item.getBook().getId().equals(bookId));
        return cartRepository.save(cart);
    }
}
