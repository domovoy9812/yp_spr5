package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.bliushtein.spr5.data.TestData.*;

public class ItemRepositoryTest extends AbstractJpaTestWithTestcontainers {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void test_findItemsInCart() {
        Item savedItem = itemRepository.save(copyItem(ITEM));
        List<Item> items = itemRepository.findItemsInCart();
        assertEquals(1, items.size());
        assertEquals(savedItem.getId(), items.getFirst().getId());
    }

    @Test
    void test_clearCart() {
        itemRepository.save(copyItem(ITEM));
        assertEquals(1, itemRepository.findItemsInCart().size());
        itemRepository.clearCart();
        assertEquals(0, itemRepository.findItemsInCart().size());
    }

    @Test
    void test_findByName() {
        Item savedItem = itemRepository.save(copyItem(ITEM));
        List<Item> foundItems = itemRepository.findByName(ITEM.getName());
        assertEquals(1, foundItems.size());
        assertEquals(savedItem.getId(), foundItems.getFirst().getId());
    }
}
