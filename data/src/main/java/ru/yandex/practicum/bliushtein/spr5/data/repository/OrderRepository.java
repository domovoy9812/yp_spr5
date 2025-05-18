package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;

//TODO merge with OrderItemRepository and rework to use R2DBC Template
@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {
}
