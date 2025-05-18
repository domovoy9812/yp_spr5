package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.bliushtein.spr5.data.TestData.*;

public class ItemRepositoryTest extends AbstractJpaTestWithTestcontainers {

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    void clearData() {
        itemRepository.deleteAll().block();
    }

    @Test
    void test_findItemsInCart() {
        Item savedItem = itemRepository.save(copyItem(ITEM)).block();
        List<Item> items = itemRepository.findItemsInCart().collectList().block();
        assertEquals(1, items.size());
        assertEquals(savedItem.getId(), items.getFirst().getId());
    }

    @Test
    void test_clearCart() {
        itemRepository.save(copyItem(ITEM)).block();
        assertEquals(1, itemRepository.findItemsInCart().collectList().block().size());
        itemRepository.clearCart().block();
        assertEquals(0, itemRepository.findItemsInCart().collectList().block().size());
    }

    @Test
    void test_findByNameLike() {
        itemRepository.saveAll(generateItems(9L, 3L)).blockLast();
        List<Item> foundItems = itemRepository.findByNameLike("%name1%",
                PageRequest.of(0, 1, Sort.by("name"))).collectList().block();
        assertEquals(1, foundItems.size());
        assertEquals("name10end", foundItems.getFirst().getName());

        foundItems = itemRepository.findByNameLike("%name1%",
                PageRequest.of(1, 1, Sort.by("name"))).collectList().block();
        assertEquals("name11end", foundItems.getFirst().getName());

        foundItems = itemRepository.findByNameLike("%name1%",
                PageRequest.of(0, 20, Sort.by("name"))).collectList().block();
        assertTrue(foundItems.stream().noneMatch(item-> "name9end".equals(item.getName())));
    }

}
