package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(@Autowired OrderRepository orderRepository, @Autowired OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }

    @Override
    public Optional<OrderDto> getOrder(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDto);
    }
}
