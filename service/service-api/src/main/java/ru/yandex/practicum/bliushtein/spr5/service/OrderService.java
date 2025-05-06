package ru.yandex.practicum.bliushtein.spr5.service;

import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getAllOrders();
    Optional<OrderDto> getOrder(Long id);
}
