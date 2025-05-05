package ru.yandex.practicum.bliushtein.spr5.controller;

import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

import java.util.List;

public class TestData {
    public static final ItemDto ITEM_DTO = new ItemDto(1L, "test item", "description", 1, 0);
    public static final ItemDto ITEM_DTO_IN_CART_1 = new ItemDto(2L, "test item 2", "description 2", 7, 1);
    public static final ItemDto ITEM_DTO_IN_CART_2 = new ItemDto(3L, "test item 3", "description 3", 5, 2);
    public static final CartDto CART_DTO = new CartDto(List.of(ITEM_DTO_IN_CART_1, ITEM_DTO_IN_CART_2), 17);
    public static final Long ORDER_ID = 4L;
}