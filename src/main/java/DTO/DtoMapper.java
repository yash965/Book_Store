package DTO;

import com.Yash.Book_Store.Entity.Cart;
import com.Yash.Book_Store.Entity.CartItem;
import com.Yash.Book_Store.Entity.Order;
import com.Yash.Book_Store.Entity.OrderItem;

import java.util.List;

public class DtoMapper {

    public static CartDto mapCart(Cart cart)
    {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());

        List<CartItemDto> items = cart.getItems().stream().map(item -> DtoMapper.getCartItemDto(item)).toList();

        cartDto.setItems(items);

        return cartDto;
    }

    private static CartItemDto getCartItemDto(CartItem item)
    {
        return new CartItemDto(
            item.getId(),
            item.getBook(),
            item.getQuantity()
        );
    }

    public static OrderDto mapOrder(Order order)
    {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());

        List<OrderItemDto> items = order.getItems().stream().map(item -> DtoMapper.getOrderItemDto(item)).toList();
        orderDto.setItems(items);

        orderDto.setDate(order.getOrderDate());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setStatus(order.getStatus());

        return orderDto;
    }

    private static OrderItemDto getOrderItemDto(OrderItem orderItem)
    {
        return new OrderItemDto(
            orderItem.getId(),
            orderItem.getBook(),
            orderItem.getQuantity(),
            orderItem.getPriceAtPurchase()
        );
    }
}
