package ru.yandex.practicum.bliushtein.spr5.service.dto;

public record ItemDto(Long id, String name, String description, int price, int amountInCart, Long imageId) { }
