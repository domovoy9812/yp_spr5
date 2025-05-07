package ru.yandex.practicum.bliushtein.spr5.data;

import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

import java.util.List;
import java.util.stream.LongStream;

public class TestData {
    public static final Item ITEM = new Item("test name", "test description", 100, 1);

    public static List<Item> generateItems(final long start, final long count) {
        return LongStream.iterate(start, i -> i + 1L).limit(count).mapToObj(
                i -> new Item("name" + i + "end", "description", 1, 0)).toList();
    }

    public static Item copyItem(Item item) {
        return new Item(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getAmountInCart());
    }
}
