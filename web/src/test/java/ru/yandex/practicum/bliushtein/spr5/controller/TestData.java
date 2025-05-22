package ru.yandex.practicum.bliushtein.spr5.controller;

import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderItemDto;

import java.util.List;

public class TestData {
    public static final String REFERER_URI = "/item/1";
    public static final ItemDto ITEM_DTO = new ItemDto(1L, "test item", "description", 1, 0, 1L);
    public static final ItemDto ITEM_DTO_IN_CART_1 = new ItemDto(2L, "test item 2", "description 2", 7, 1, 1L);
    public static final ItemDto ITEM_DTO_IN_CART_2 = new ItemDto(3L, "test item 3", "description 3", 5, 2, 1L);
    public static final CartDto CART_DTO = new CartDto(List.of(ITEM_DTO_IN_CART_1, ITEM_DTO_IN_CART_2), 17);
    public static final Long ORDER_ID = 4L;
    public static final OrderItemDto ORDER_ITEM_DTO = new OrderItemDto(5L, "name 5", "description 5", 5, 5, 1L);
    public static final OrderDto ORDER_DTO = new OrderDto(ORDER_ID, List.of(ORDER_ITEM_DTO), 61);
}