package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(@Autowired ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void deleteAll() {
        itemRepository.deleteAll();
    }

    @Override
    public ItemDto createItem(String name) {
        Item item = itemRepository.save(new Item(name));
        return new ItemDto(item.getId(), item.getName());
    }

    @Override
    public List<ItemDto> getAllItems() {
        return List.of();
    }

    @Override
    public Optional<ItemDto> findItemByName(String name) {
        return itemRepository.findByName(name).map(item -> new ItemDto(item.getId(), item.getName()));
    }
}
