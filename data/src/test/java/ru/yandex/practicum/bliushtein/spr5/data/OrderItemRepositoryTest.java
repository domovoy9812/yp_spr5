package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderItemRepositoryTest extends AbstractJpaTestWithTestcontainers {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void test_findByOrderId() {
        Order order = orderRepository.save(new Order(1)).block();
        OrderItem item = new OrderItem(order.getId(),
                new Item(2L, "name", "description", 1 , 0));
        orderItemRepository.save(item).block();
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId()).collectList().block();
        assertEquals(1, items.size());
    }

    @AfterEach
    void clear() {
        orderItemRepository.deleteAll().block();
        orderRepository.deleteAll().block();
    }
}
