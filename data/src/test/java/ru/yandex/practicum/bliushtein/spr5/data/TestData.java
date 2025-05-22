package ru.yandex.practicum.bliushtein.spr5.data;

import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;

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
    public static final Order NOT_SAVED_ORDER = new Order(ITEM.getPrice());
    public static final Order SAVED_ORDER = new Order(1L, NOT_SAVED_ORDER.getTotalPrice());
    public static final OrderItem SAVED_ORDER_ITEM = new OrderItem(SAVED_ORDER.getId(), ITEM);

}
