package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.bliushtein.spr5.data.TestData.*;

public class ItemRepositoryTest extends AbstractJpaTestWithTestcontainers {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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
    void test_findByNameLike() {
        itemRepository.saveAll(generateItems(9L, 3L));
        Slice<Item> itemsSlice = itemRepository.findByNameLike("%name1%",
                PageRequest.of(0, 1, Sort.by("name")));
        List<Item> foundItems = itemsSlice.getContent();
        assertEquals(1, foundItems.size());
        assertEquals("name10end", foundItems.getFirst().getName());

        itemsSlice = itemRepository.findByNameLike("%name1%",
                PageRequest.of(1, 1, Sort.by("name")));
        foundItems = itemsSlice.getContent();
        assertEquals("name11end", foundItems.getFirst().getName());

        itemsSlice = itemRepository.findByNameLike("%name1%",
                PageRequest.of(0, 20, Sort.by("name")));
        assertTrue(itemsSlice.getContent().stream().noneMatch(item-> "name9end".equals(item.getName())));
    }
}
