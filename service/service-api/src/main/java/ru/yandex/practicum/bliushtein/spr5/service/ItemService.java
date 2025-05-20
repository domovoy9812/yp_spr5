package ru.yandex.practicum.bliushtein.spr5.service;

import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

public interface ItemService {
    Mono<ItemDto> createItem(String name, String description, int price, byte[] image);
    Mono<ItemDto> findItemById(Long itemId);
    Mono<PagedItemsDto> searchItems(String name, int pageNumber, int pageSize, ItemSort sort);
}
