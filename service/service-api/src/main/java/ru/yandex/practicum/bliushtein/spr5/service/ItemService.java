package ru.yandex.practicum.bliushtein.spr5.service;

import ru.yandex.practicum.bliushtein.spr5.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    void deleteAll();
    ItemDto createItem(String name);
    List<ItemDto> getAllItems();
    Optional<ItemDto> findItemByName(String name);
}
