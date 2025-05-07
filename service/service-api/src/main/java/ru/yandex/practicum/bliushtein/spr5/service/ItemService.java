package ru.yandex.practicum.bliushtein.spr5.service;

import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

import java.util.Optional;

public interface ItemService {
    ItemDto createItem(String name, String description, int price, byte[] image);
    Optional<ItemDto> findItemById(Long itemId);
    PagedItemsDto searchItems(String name, int pageNumber, int pageSize, ItemSort sort);
}
