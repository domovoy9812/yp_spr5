package ru.yandex.practicum.bliushtein.spr5.service.dto;

import java.util.List;

public record CartDto (List<ItemDto> items, int totalPrice) {

    public CartDto(List<ItemDto> items) {
        this(items, items.stream().mapToInt(it -> it.amountInCart() * it.price()).sum());
    }

}
