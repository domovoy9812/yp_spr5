package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Objects;

//TODO restore transaction support
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
    //@Transactional
    public void increaseAmountInCart(Long itemId) {
        Objects.requireNonNull(itemId);
        itemRepository.findById(itemId).blockOptional().ifPresentOrElse(this::increaseAmountInCart,
                () -> ShopException.throwItemNotFound(itemId));
    }

    @Override
    //@Transactional
    public void decreaseAmountInCart(Long itemId) {
        Objects.requireNonNull(itemId);
        itemRepository.findById(itemId).blockOptional().ifPresentOrElse(this::decreaseAmountInCart,
                () -> ShopException.throwItemNotFound(itemId));
    }

    @Override
    //@Transactional
    public void removeFromCart(Long itemId) {
        Objects.requireNonNull(itemId);
        itemRepository.findById(itemId).blockOptional().ifPresentOrElse(this::removeFromCart,
                () -> ShopException.throwItemNotFound(itemId));
    }

    @Override
    public CartDto getCart() {
        List<Item> items = itemRepository.findItemsInCart().collectList().block();
        return new CartDto(items.stream().map(itemMapper::toDto).toList(), calculateTotalPrice(items));
    }

    private int calculateTotalPrice(List<Item> items) {
        return items.stream().mapToInt(it -> it.getAmountInCart() * it.getPrice()).sum();
    }

    @Override
    //@Transactional
    public Long buy() {
        List<Item> items = itemRepository.findItemsInCart().collectList().block();
        if (items.isEmpty()) {
            ShopException.throwCartIsEmpty();
        }
        Order order = new Order(calculateTotalPrice(items));
        Order createdOrder = orderRepository.save(order).block();
        List<OrderItem> orderItems = items.stream()
                .map(item -> new OrderItem(createdOrder.getId(), item)).toList();
        orderItemRepository.saveAll(orderItems).blockLast();
        itemRepository.clearCart().block();
        return createdOrder.getId();
    }

    private void increaseAmountInCart(Item item) {
        item.setAmountInCart(item.getAmountInCart() + 1);
        itemRepository.save(item).block();
    }

    private void decreaseAmountInCart(Item item) {
        if (item.getAmountInCart() == 0) {
            ShopException.throwAmountInCartCantBeNegative();
        } else {
            item.setAmountInCart(item.getAmountInCart() - 1);
            itemRepository.save(item).block();
        }
    }

    private void removeFromCart(Item item) {
        item.setAmountInCart(0);
        itemRepository.save(item).block();
    }

}
