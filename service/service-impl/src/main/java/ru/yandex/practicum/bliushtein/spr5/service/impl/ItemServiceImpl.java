package ru.yandex.practicum.bliushtein.spr5.service.impl;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ImageRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.service.ItemSort;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(@Autowired ItemRepository itemRepository,
                           @Autowired ImageRepository imageRepository,
                           @Autowired ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    @Transactional
    public Mono<ItemDto> createItem(String name, String description, int price, byte[] imageData) {
        if (price <= 0) {
            return Mono.error(ShopException.priceShouldBePositive(price));
        }
        if (imageData == null) {
            return itemRepository.save(new Item(name, description, price, 0, null)).map(itemMapper::toDto);
        } else {
            return imageRepository.save(new Image(imageData))
                    .flatMap(image -> itemRepository.save(
                            new Item(name, description, price, 0, image.getId())))
                    .map(itemMapper::toDto);
        }
    }

    @Override
    public Mono<ItemDto> findItemById(Long itemId) {
        return itemRepository.findById(itemId).map(itemMapper::toDto);
    }

    @Override
    public Mono<PagedItemsDto> searchItems(String name, int pageNumber, int pageSize, ItemSort sort) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort.getColumns()));
        Flux<Item> itemFlux;
        if (StringUtils.isBlank(name)) {
            itemFlux = itemRepository.findAllBy(pageable);
        } else {
            itemFlux = itemRepository.findByNameLike("%" + name + "%", pageable);
        }
        return itemFlux.map(itemMapper::toDto).collectList().zipWith(itemRepository.count())
                .map(pageSource -> new PagedItemsDto(pageSource.getT1(), pageNumber, pageSize,
                        pageSource.getT2()));
    }

}