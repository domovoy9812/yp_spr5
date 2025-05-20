package ru.yandex.practicum.bliushtein.spr5.service;

import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;

public interface CartService {
    Mono<Void> increaseAmountInCart(Long itemId);
    Mono<Void> decreaseAmountInCart(Long itemId);
    Mono<Void> removeFromCart(Long itemId);
    Mono<CartDto> getCart();
    Mono<Long> buy();
}
