package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderWithItemsRepository;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.PaymentService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final ItemRepository itemRepository;
    private final OrderWithItemsRepository orderRepository;
    private final ItemMapper itemMapper;
    private final PaymentService paymentService;
    public CartServiceImpl(@Autowired ItemRepository itemRepository,
                           @Autowired OrderWithItemsRepository orderRepository,
                           @Autowired ItemMapper itemMapper,
                           @Autowired PaymentService paymentService) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.itemMapper = itemMapper;
        this.paymentService = paymentService;
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "cart", key = "'default'"),
                    @CacheEvict(value = "itemsSearchResult", allEntries = true),
                    @CacheEvict(value = "items")
            }
    )
    public Mono<Void> increaseAmountInCart(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(ShopException.itemNotFound(itemId)))
                .flatMap(this::increaseAmountInCart);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "cart", key = "'default'"),
                    @CacheEvict(value = "itemsSearchResult", allEntries = true),
                    @CacheEvict(value = "items")
            }
    )
    public Mono<Void> decreaseAmountInCart(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(ShopException.itemNotFound(itemId)))
                .flatMap(this::decreaseAmountInCart);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "cart", key = "'default'"),
                    @CacheEvict(value = "itemsSearchResult", allEntries = true),
                    @CacheEvict(value = "items")
            }
    )
    public Mono<Void> removeFromCart(Long itemId) {
        return itemRepository.findById(itemId)
                .switchIfEmpty(Mono.error(ShopException.itemNotFound(itemId)))
                .flatMap(this::removeFromCart);
    }

    @Override
    @Cacheable(value = "cart", key = "'default'")
    public Mono<CartDto> getCart() {
        return itemRepository.findItemsInCart().map(itemMapper::toDto).collectList().map(CartDto::new);
    }

    @Override
    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "cart", key = "'default'"),
                    @CacheEvict(value = "itemsSearchResult", allEntries = true),
                    @CacheEvict(value = "items", allEntries = true)
            }
    )
    public Mono<Long> buy() {
        return itemRepository.findItemsInCart()
                .collectList().flatMap(this::createOrder)
                .flatMap(id -> itemRepository.clearCart().then(Mono.just(id)));
    }

    private Mono<Long> createOrder(List<Item> items) {
        if (items.isEmpty()) {
            return Mono.error(ShopException.cartIsEmpty());
        }
        Order order = new Order(items);
        return paymentService.pay(order.getTotalPrice()).then(orderRepository.save(order, items));
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
