package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderWithItems;

import java.util.List;

@Repository
public class OrderWithItemsRepository {
    private final OrderItemRepository itemRepository;
    private final OrderRepository orderRepository;
    public OrderWithItemsRepository(@Autowired OrderItemRepository itemRepository,
                                    @Autowired OrderRepository orderRepository) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    public Mono<Long> save(Order order, List<Item> items) {
        return orderRepository.save(order).map(Order::getId)
                .flatMap(orderId -> itemRepository.saveAll(createOrderItems(items, orderId)).collectList()
                        .then(Mono.just(orderId)));
    }

    private List<OrderItem> createOrderItems(List<Item> items, Long orderId) {
        return items.stream().map(item -> new OrderItem(orderId, item)).toList();
    }

    public Mono<OrderWithItems> findById(Long orderId) {
        return orderRepository.findById(orderId).zipWith(itemRepository.findByOrderId(orderId).collectList())
                .map(OrderWithItems::new);
    }

    public Flux<OrderWithItems> findAll() {
        return orderRepository.findAll().flatMap(order -> itemRepository.findByOrderId(order.getId()).collectList()
                .map(items -> Tuples.of(order, items))).map(OrderWithItems::new);
    }
}
