package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemMapper itemMapper;
    public CartServiceImpl(@Autowired ItemRepository itemRepository,
                           @Autowired OrderRepository orderRepository,
                           @Autowired OrderItemRepository orderItemRepository,
                           @Autowired ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    @Transactional
    public Mono<Void> increaseAmountInCart(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(ShopException.itemNotFound(itemId)))
                .flatMap(this::increaseAmountInCart);
    }

    @Override
    @Transactional
    public Mono<Void> decreaseAmountInCart(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(ShopException.itemNotFound(itemId)))
                .flatMap(this::decreaseAmountInCart);
    }

    @Override
    @Transactional
    public Mono<Void> removeFromCart(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(ShopException.itemNotFound(itemId)))
                .flatMap(this::removeFromCart);
    }

    @Override
    public Mono<CartDto> getCart() {
        return itemRepository.findItemsInCart().map(itemMapper::toDto).collectList().map(CartDto::new);
    }

    @Override
    @Transactional
    public Mono<Long> buy() {
        return itemRepository.findItemsInCart()
                .collectList().flatMap(this::createOrder);
    }

    private Mono<Long> createOrder(List<Item> items) {
        if (items.isEmpty()) {
            return Mono.error(ShopException.cartIsEmpty());
        }
        return orderRepository.save(new Order(items))
                .flatMap(order -> this.saveOrderItems(order.getId(), items));
    }

    private Mono<Long> saveOrderItems(Long orderId, List<Item> items) {
        return orderItemRepository.saveAll(createOrderItems(items, orderId))
                .then(itemRepository.clearCart())
                .then(Mono.just(orderId));
    }

    private List<OrderItem> createOrderItems(List<Item> items, Long orderId) {
        return items.stream().map(item -> new OrderItem(orderId, item)).toList();
    }

    private Mono<Void> increaseAmountInCart(Item item) {
        item.setAmountInCart(item.getAmountInCart() + 1);
        return itemRepository.save(item).then();
    }

    private Mono<Void> decreaseAmountInCart(Item item) {
        if (item.getAmountInCart() == 0) {
            return Mono.error(ShopException.amountInCartCantBeNegative());
        } else {
            item.setAmountInCart(item.getAmountInCart() - 1);
            return itemRepository.save(item).then();
        }
    }

    private Mono<Void> removeFromCart(Item item) {
        item.setAmountInCart(0);
        return itemRepository.save(item).then();
    }

}
