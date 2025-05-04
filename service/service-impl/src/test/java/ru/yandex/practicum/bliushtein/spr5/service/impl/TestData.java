package ru.yandex.practicum.bliushtein.spr5.service.impl;

import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

public class TestData {
    public static final int ITEM_AMOUNT_IN_CART_0 = 0;
    public static final Long NOT_EXISTING_ITEM_ID = -1L;
    public static final Item ITEM_1 = new Item(1L, "name", "description", 100,
            1);
    public static final Item ITEM_NOT_IN_CART = new Item(1L, "name", "description", 100,
            ITEM_AMOUNT_IN_CART_0);

    public static Item copyItem(Item item) {
        return new Item(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmountInCart());
    }
}