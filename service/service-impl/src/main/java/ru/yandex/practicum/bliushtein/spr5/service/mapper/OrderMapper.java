package ru.yandex.practicum.bliushtein.spr5.service.mapper;

import org.springframework.stereotype.Component;
import reactor.util.function.Tuple2;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderItemDto;

import java.util.List;

@Component
public class OrderMapper implements Mapper<Tuple2<Order, List<OrderItem>>, OrderDto> {
    @Override
    public OrderDto toDto(Tuple2<Order, List<OrderItem>> orderSource) {
        List<OrderItemDto> orderItemDtos = orderSource.getT2().stream().map(this::mapOrderItem).toList();
        return new OrderDto(orderSource.getT1().getId(), orderItemDtos, orderSource.getT1().getTotalPrice());
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
