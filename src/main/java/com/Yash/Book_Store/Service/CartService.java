package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.DTO.CartDto;
import com.Yash.Book_Store.DTO.DtoMapper;
import com.Yash.Book_Store.Entity.BookEntry;
import com.Yash.Book_Store.Entity.Cart;
import com.Yash.Book_Store.Entity.CartItem;
import com.Yash.Book_Store.Entity.User;
import com.Yash.Book_Store.Repository.BookStore_Repository;
import com.Yash.Book_Store.Repository.CartRepository;
import com.Yash.Book_Store.Repository.User_Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

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
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username)
                    .orElseThrow(()-> new RuntimeException("Authenticated user not found"));
        } catch (RuntimeException e) {
            log.error("Could not retrieve current user from security context. This should not happen for a secured endpoint", e);
            throw new IllegalStateException("User not authenticated Exception");
        }
    }

    @Transactional
    public Cart getCartForCurrentUser()
    {
        User user = getCurrentUser();
        return cartRepository.findByUser(user).orElseGet(() -> {
                Cart newCart = new Cart();
                log.info("No, existing cart, New Cart created");
                newCart.setUser(user);
                return cartRepository.save(newCart);
        });

    }

    @Transactional
    public CartDto getCartDtoForCurrentUser()
    {
        User user = getCurrentUser();
        Cart cart = getCartForCurrentUser();

        return DtoMapper.mapCart(cart);
    }

    @Transactional
    public CartDto addBookToCart(Long bookId, int quantity)
    {
        Cart cart = getCartForCurrentUser();
        BookEntry book = null;
        try {
            book = bookStoreRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
            log.info("Book Found in the list");
        } catch (RuntimeException e) {
            log.error("Book not found in the list", e);
            throw new RuntimeException(e);
        }

        for(CartItem cartItem : cart.getItems())
        {
            if(cartItem.getBook().getId().equals(bookId))
            {
                cartItem.setQuantity(cartItem.getQuantity()+quantity);

                log.info("Item book [{}] Added, quantity [{}] to existing Cart", book.getBookName(), quantity);
                cartRepository.save(cart);

                return DtoMapper.mapCart(cart);
            }
        }

        CartItem item = new CartItem();
        item.setBook(book);
        item.setQuantity(quantity);
        item.setCart(cart);
        cart.getItems().add(item);

        log.info("Item book [{}] Added, quantity [{}] to New Cart", book.getBookName(), quantity);

        cartRepository.save(cart);
        return DtoMapper.mapCart(cart);
    }

    @Transactional
    public CartDto removeBookFromCart(Long bookId) {
        Cart cart = getCartForCurrentUser();

        for(CartItem item : cart.getItems())
        {
            BookEntry book = item.getBook();

            if(book.getId().equals(bookId))
            {
                int quantity = item.getQuantity()-1;

                if(quantity <= 0)
                {
                    cart.getItems().remove(item);
                    log.info("Book Present [{}], and removed from cart", bookId);
                }
                else
                {
                    item.setQuantity(quantity);
                    log.info("Book Present [{}], and quantity is reduced", bookId);
                }

                break;
            }
        }

        cartRepository.save(cart);
        return DtoMapper.mapCart(cart);
    }
}
