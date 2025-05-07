package ru.yandex.practicum.bliushtein.spr5.service.dto;
import java.util.List;

public record PagedItemsDto(List<ItemDto> items,
                             boolean firstPage,
                             boolean lastPage) {
}
