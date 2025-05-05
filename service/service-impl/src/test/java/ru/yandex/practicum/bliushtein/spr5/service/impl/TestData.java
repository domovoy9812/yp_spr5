package ru.yandex.practicum.bliushtein.spr5.service.impl;

import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;

import java.util.List;

public class TestData {
    public static final int ITEM_AMOUNT_IN_CART_0 = 0;
    public static final Long NOT_EXISTING_ITEM_ID = -1L;
    public static final Item ITEM_1 = new Item(1L, "name", "description", 100,
            1);
    public static final Item ITEM_2 = new Item(1L, "name 2", "description 2", 10,
            3);
    public static final int ITEM_1_AND_ITEM_2_TOTAL_PRICE = ITEM_1.getPrice() * ITEM_1.getAmountInCart()
            + ITEM_2.getPrice() * ITEM_2.getAmountInCart();
    public static final Item ITEM_NOT_IN_CART = new Item(1L, "name", "description", 100,
            ITEM_AMOUNT_IN_CART_0);
    public static final Order CREATED_ORDER = new Order(1L, List.of(), 10);
    public static Item copyItem(Item item) {
        return new Item(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmountInCart());
    }
}