package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.Entity.*;
import com.Yash.Book_Store.Repository.CartRepository;
import com.Yash.Book_Store.Repository.OrderRepository;
import com.Yash.Book_Store.Repository.User_Repository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final User_Repository userRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, User_Repository userRepository)
    {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));
    }

    public List<Order> getOrderHistoryForCurrentUser()
    {
        User user = getCurrentUser();
        return orderRepository.findByUser(user);
    }

    @Transactional
    public Order createOrderFromCart()
    {
        User user = getCurrentUser();
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found for user"));

        if(cart.getItems().isEmpty())
        {
            throw new IllegalStateException("Cannot create an order from an empty cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        double totalAmount = 0.0;

        for(CartItem item : cart.getItems())
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(item.getBook());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getBook().getPrice());
            order.getItems().add(orderItem);
            totalAmount += orderItem.getPriceAtPurchase() * orderItem.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }
}
