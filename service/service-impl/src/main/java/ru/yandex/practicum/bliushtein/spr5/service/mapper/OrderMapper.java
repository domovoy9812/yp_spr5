package ru.yandex.practicum.bliushtein.spr5.service.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderItemDto;

import java.util.List;

@Component
public class OrderMapper implements Mapper<Order, OrderDto> {
    @Override
    public OrderDto toDto(Order order) {
        List<OrderItemDto> orderItems = order.getOrderItems().stream().map(this::mapOrderItem).toList();
        return new OrderDto(order.getId(), orderItems, order.getTotalPrice());
    }

    private OrderItemDto mapOrderItem(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getItem().getId(),
                orderItem.getItem().getName(),
                orderItem.getItem().getDescription(),
                orderItem.getPrice(),
                orderItem.getAmount());
    }
}
