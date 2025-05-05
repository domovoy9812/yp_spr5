package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
