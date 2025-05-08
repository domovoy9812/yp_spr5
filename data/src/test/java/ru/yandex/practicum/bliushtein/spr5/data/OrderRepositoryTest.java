package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;

public class OrderRepositoryTest extends AbstractJpaTestWithTestcontainers {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void contextLoads() {
    }
}
