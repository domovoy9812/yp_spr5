package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    public ItemServiceImpl(@Autowired ItemRepository itemRepository, @Autowired ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    @Transactional
    public ItemDto createItem(String name, String description, int price) {
        Item item = itemRepository.save(new Item(name, description, price, 0));
        return itemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll().stream().map(itemMapper::toDto).toList();
    }

    @Override
    public List<ItemDto> findItemsByName(String name) {
        Objects.requireNonNull(name);
        return itemRepository.findByName(name).stream().map(itemMapper::toDto)
                .toList();
    }

    @Override
    public Optional<ItemDto> findItemById(Long itemId) {
        Objects.requireNonNull(itemId);
        return itemRepository.findById(itemId).map(itemMapper::toDto);
    }

}