package com.Yash.Book_Store.Controller;

import com.Yash.Book_Store.DTO.OrderDto;
import com.Yash.Book_Store.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder()
    {
        OrderDto order = orderService.createOrderFromCart();
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderHistory()
    {
        List<OrderDto> orders = orderService.getOrderHistoryForCurrentUser();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
