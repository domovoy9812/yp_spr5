package ru.yandex.practicum.bliushtein.spr5.service.impl;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.service.ItemSort;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

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
        if (price <= 0) {
            ShopException.throwPriceShouldBePositive(price);
        }
        Item item = itemRepository.save(new Item(name, description, price, 0));
        return itemMapper.toDto(item);
    }

    @Override
    public Optional<ItemDto> findItemById(Long itemId) {
        Objects.requireNonNull(itemId);
        return itemRepository.findById(itemId).map(itemMapper::toDto);
    }

    @Override
    public PagedItemsDto searchItems(String name, int pageNumber, int pageSize, ItemSort sort) {
        Slice<Item> items;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort.getColumns()));
        if (StringUtils.isBlank(name)) {
            items = itemRepository.findAll(pageable);
        } else {
            items = itemRepository.findByNameLike("%" + name + "%", pageable);
        }
        return new PagedItemsDto(items.getContent().stream().map(itemMapper::toDto).toList(),
                items.isFirst(), items.isLast());
    }

}