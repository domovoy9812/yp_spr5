package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.Optional;

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
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().collectList().block().stream().map(this::getOrderDtoSource).map(orderMapper::toDto).toList();
    }

    @Override
    public Optional<OrderDto> getOrder(Long id) {
        return orderRepository.findById(id).blockOptional().map(this::getOrderDtoSource).map(orderMapper::toDto);
    }

    private Tuple2<Order, List<OrderItem>> getOrderDtoSource(Order order) {
        return Tuples.of(order, orderItemRepository.findByOrderId(order.getId()).collectList().block());
    }
}
