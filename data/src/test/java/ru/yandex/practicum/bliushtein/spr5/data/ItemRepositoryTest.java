package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemRepositoryTest extends AbstractJpaTestWithTestcontainers {

    @Autowired
    ItemRepository itemRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testItemRepository() {
        Item newItem = new Item("test name");
        itemRepository.save(newItem);
        List<Item> allItems = itemRepository.findAll();
        assertEquals(1, allItems.size());
        assertEquals(newItem.getName(), allItems.getFirst().getName());
    }
}
