package DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private List<OrderItemDto> items;
    private LocalDateTime date;
    private double totalAmount;
    private String status;
}
