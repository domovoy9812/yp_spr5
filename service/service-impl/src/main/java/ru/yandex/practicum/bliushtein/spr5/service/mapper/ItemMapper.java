package ru.yandex.practicum.bliushtein.spr5.service.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

@Component
public class ItemMapper implements Mapper<Item, ItemDto> {
    @Override
    public ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getPrice(),
                item.getAmountInCart());
    }

}