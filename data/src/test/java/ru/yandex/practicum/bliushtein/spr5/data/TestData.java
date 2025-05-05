package ru.yandex.practicum.bliushtein.spr5.data;

import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

public class TestData {
    public static final Item ITEM = new Item("test name", "test description", 100, 1);
    public static Item copyItem(Item item) {
        return new Item(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmountInCart());
    }
}
