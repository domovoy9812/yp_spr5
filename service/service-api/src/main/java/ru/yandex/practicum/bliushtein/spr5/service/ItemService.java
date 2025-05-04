package ru.yandex.practicum.bliushtein.spr5.service;

import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    ItemDto createItem(String name, String description, int price);
    List<ItemDto> getAllItems();
    List<ItemDto> findItemsByName(String name);
    Optional<ItemDto> findItemById(Long id);
    void increaseAmountInCart(Long itemId);
    void decreaseAmountInCart(Long itemId);

}
