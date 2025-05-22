package ru.yandex.practicum.bliushtein.spr5.service.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderWithItems;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderItemDto;

import java.util.List;

@Component
public class OrderMapper implements Mapper<OrderWithItems, OrderDto> {
    @Override
    public OrderDto toDto(OrderWithItems order) {
        List<OrderItemDto> orderItemDtos = order.items().stream().map(this::mapOrderItem).toList();
        return new OrderDto(order.order().getId(), orderItemDtos, order.order().getTotalPrice());
    }

    private OrderItemDto mapOrderItem(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getItemId(),
                orderItem.getItemName(),
                orderItem.getItemDescription(),
                orderItem.getPrice(),
                orderItem.getAmount(),
                orderItem.getImageId());
    }
}
