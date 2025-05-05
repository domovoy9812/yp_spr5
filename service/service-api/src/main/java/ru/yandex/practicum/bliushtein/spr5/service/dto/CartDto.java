package ru.yandex.practicum.bliushtein.spr5.service.dto;

import java.util.List;

public record CartDto (List<ItemDto> items, int totalPrice) {
}
