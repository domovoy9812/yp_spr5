package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderWithItemsRepository;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.OrderMapper;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderWithItemsRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(@Autowired OrderWithItemsRepository orderRepository,
                            @Autowired OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Flux<OrderDto> getAllOrders() {
        return orderRepository.findAll().map(orderMapper::toDto);
    }

    @Override
    public Mono<OrderDto> getOrder(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

}
