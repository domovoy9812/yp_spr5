package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

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
        assertThrows(NullPointerException.class, () -> itemService.findItemById(null).isPresent());
        verify(itemRepository, never()).findById(any());
    }

    @Test
    void test_increaseAmountInCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        itemService.increaseAmountInCart(ITEM_1.getId());
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_1.getAmountInCart() + 1, storedItem.getAmountInCart());
    }

    @Test
    void test_increaseAmountInCart_ItemNotFound() {
        when(itemRepository.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Optional.empty());
        assertThrows(ShopException.class, () -> itemService.increaseAmountInCart(NOT_EXISTING_ITEM_ID));
    }

    @Test
    void test_increaseAmountInCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> itemService.increaseAmountInCart(null));
        verify(itemRepository, never()).findById(any());
    }

    @Test
    void test_decreaseAmountInCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        itemService.decreaseAmountInCart(ITEM_1.getId());
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepository).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_1.getAmountInCart() - 1, storedItem.getAmountInCart());
    }

    @Test
    void test_decreaseAmountInCart_ItemNotInCart() {
        when(itemRepository.findById(ITEM_NOT_IN_CART.getId())).thenReturn(Optional.of(ITEM_NOT_IN_CART));
        assertThrows(ShopException.class, () -> itemService.decreaseAmountInCart(ITEM_NOT_IN_CART.getId()));
        verify(itemRepository, never()).save(any());
    }

    @Test
    void test_decreaseAmountInCart_ItemNotFound() {
        when(itemRepository.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Optional.empty());
        assertThrows(ShopException.class, () -> itemService.decreaseAmountInCart(NOT_EXISTING_ITEM_ID));
    }

    @Test
    void test_decreaseAmountInCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> itemService.decreaseAmountInCart(null));
        verify(itemRepository, never()).findById(any());
    }
}
