package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ImageRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.service.ItemSort;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.service.impl.TestData.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ItemServiceImpl.class, ItemMapper.class})
public class ItemServiceImplTest {
    @Autowired
    ItemService itemService;

    @MockitoBean
    ItemRepository itemRepository;

    @MockitoBean
    ImageRepository imageRepository;

    @Test
    void test_findItemById() {
        when(itemRepository.findById(ITEM_1.getId())).thenReturn(Optional.of(ITEM_1));
        Optional<ItemDto> result = itemService.findItemById(ITEM_1.getId());
        assertTrue(result.isPresent());
        ItemDto item = result.get();
        assertEquals(ITEM_1.getId(), item.id());
        assertEquals(ITEM_1.getName(), item.name());
        assertEquals(ITEM_1.getDescription(), item.description());
        assertEquals(ITEM_1.getPrice(), item.price());
        assertEquals(ITEM_1.getAmountInCart(), item.amountInCart());
    }

    @Test
    void test_findItemById_ItemNotFound() {
        when(itemRepository.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Optional.empty());
        assertFalse(itemService.findItemById(NOT_EXISTING_ITEM_ID).isPresent());
    }

    @Test
    void test_findItemById_NullItemId() {
        assertThrows(NullPointerException.class, () -> itemService.findItemById(null));
        verify(itemRepository, never()).findById(any());
    }

    @Test
    void test_createItem_priceShouldBePositive() {
        assertThrows(ShopException.class, () -> itemService.createItem(ITEM_1.getName(), ITEM_1.getDescription(), 0, null));
    }

    @Test
    void test_searchItems_findAll() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        when(itemRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(ITEM_1), pageable, 100));
        PagedItemsDto items = itemService.searchItems(null, 0, 5, ItemSort.NO);
        assertEquals(1, items.items().size());
        assertEquals(ITEM_1.getName(), items.items().getFirst().name());
    }

    @Test
    void test_searchItems_findByName() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id"));
        when(itemRepository.findByNameLike("%name%", pageable)).thenReturn(new SliceImpl<>(List.of(ITEM_1), pageable, true));
        PagedItemsDto items = itemService.searchItems("name", 0, 5, ItemSort.NO);
        assertEquals(1, items.items().size());
        assertEquals(ITEM_1.getName(), items.items().getFirst().name());
    }
}
