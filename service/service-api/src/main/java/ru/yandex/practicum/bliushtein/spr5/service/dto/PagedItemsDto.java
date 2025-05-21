package ru.yandex.practicum.bliushtein.spr5.service.dto;
import java.util.List;

public record PagedItemsDto(List<ItemDto> items,
                             boolean firstPage,
                             boolean lastPage) {
    public PagedItemsDto(List<ItemDto> items, int pageNumber, int pageSize, long count) {
        this(items, pageNumber == 0,count <= (long) (pageNumber + 1) * pageSize);
    }
}
