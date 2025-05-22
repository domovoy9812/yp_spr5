package ru.yandex.practicum.bliushtein.spr5.data.entity;

import reactor.util.function.Tuple2;

import java.util.List;

public record OrderWithItems(Order order, List<OrderItem> items) {
    public OrderWithItems(Tuple2<Order, List<OrderItem>> input) {
        this(input.getT1(), input.getT2());
    }
}
