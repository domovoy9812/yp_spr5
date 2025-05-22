package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderRepositoryTest extends AbstractRepositoryTestWithTestcontainers {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void test() {
        Long count = orderRepository.save(new Order(1)).then(orderRepository.count()).block();
        assertEquals(1, count);
    }

    @AfterEach
    void clear() {
        orderRepository.deleteAll().block();
    }
}
