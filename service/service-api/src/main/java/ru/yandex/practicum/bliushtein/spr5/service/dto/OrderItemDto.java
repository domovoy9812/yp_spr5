package ru.yandex.practicum.bliushtein.spr5.service.dto;

public record OrderItemDto(Long id, String name, String description, int price, int amount, Long imageId) {
}
