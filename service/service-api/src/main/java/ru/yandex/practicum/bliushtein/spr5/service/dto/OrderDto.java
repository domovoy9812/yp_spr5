package ru.yandex.practicum.bliushtein.spr5.service.dto;

import java.util.List;

public record OrderDto(Long id, List<OrderItemDto> orderItems, int totalPrice) {
}
