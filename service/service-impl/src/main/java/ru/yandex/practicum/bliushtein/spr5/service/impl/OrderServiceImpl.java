package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.OrderMapper;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(@Autowired OrderRepository orderRepository,
                            @Autowired OrderItemRepository orderItemRepository,
                            @Autowired OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Flux<OrderDto> getAllOrders() {
        return orderRepository.findAll().flatMap(this::getOrderDtoSource).map(orderMapper::toDto);
    }

    @Override
    public Mono<OrderDto> getOrder(Long id) {
        return orderRepository.findById(id).flatMap(this::getOrderDtoSource).map(orderMapper::toDto);
    }

    private Mono<Tuple2<Order, List<OrderItem>>> getOrderDtoSource(Order order) {
        return orderItemRepository.findByOrderId(order.getId()).collectList()
                .map(orderItems -> Tuples.of(order, orderItems));
    }
}
