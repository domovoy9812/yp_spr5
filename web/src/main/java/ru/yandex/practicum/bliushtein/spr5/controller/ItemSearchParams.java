package ru.yandex.practicum.bliushtein.spr5.controller;

import ru.yandex.practicum.bliushtein.spr5.service.ItemSort;

public class ItemSearchParams {

    private String name;
    private ItemSort sort;
    private int pageSize;
    private int pageNumber;

    public ItemSearchParams() {}

    public ItemSearchParams(String name, int pageNumber, int pageSize, ItemSort sort) {
        this.name = name;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemSort getSort() {
        return this.sort == null ? ItemSort.NO : this.sort;
    }

    public void setSort(ItemSort sort) {
        this.sort = sort;
    }

    public int getPageSize() {
        return this.pageSize == 0 ? 5 : this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
