package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.DTO.DtoMapper;
import com.Yash.Book_Store.DTO.OrderDto;
import com.Yash.Book_Store.Entity.*;
import com.Yash.Book_Store.Repository.CartRepository;
import com.Yash.Book_Store.Repository.OrderRepository;
import com.Yash.Book_Store.Repository.User_Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

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

    public List<OrderDto> getOrderHistoryForCurrentUser()
    {
        User user = getCurrentUser();

        return orderRepository.findByUser(user).stream()
                .map(order -> DtoMapper.mapOrder(order)).toList();
    }

    @Transactional
    public OrderDto createOrderFromCart()
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
        cart.getItems().clear();            // Clear Cart
        cartRepository.save(cart);

        orderRepository.save(order);

        log.info("Order Details -> OrderAmount - {}, Status - {}", totalAmount, order.getStatus());

        return DtoMapper.mapOrder(order);
    }
}
