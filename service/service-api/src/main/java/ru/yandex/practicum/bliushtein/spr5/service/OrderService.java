package ru.yandex.practicum.bliushtein.spr5.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;

public interface OrderService {
    Flux<OrderDto> getAllOrders();
    Mono<OrderDto> getOrder(Long id);
}
