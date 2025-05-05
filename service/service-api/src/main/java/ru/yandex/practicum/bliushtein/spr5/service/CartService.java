package ru.yandex.practicum.bliushtein.spr5.service;

import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;

public interface CartService {
    void increaseAmountInCart(Long itemId);
    void decreaseAmountInCart(Long itemId);
    void removeFromCart(Long itemId);
    CartDto getCart();
    Long buy();
}
