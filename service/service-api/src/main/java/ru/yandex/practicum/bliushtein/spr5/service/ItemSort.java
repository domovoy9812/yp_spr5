package ru.yandex.practicum.bliushtein.spr5.service;

public enum ItemSort {
    NAME("name"),
    PRICE("price"),
    NO("id");

    private final String[] columns;
    public String[] getColumns() {
        return columns;
    }

    ItemSort(String... columns) {
        this.columns = columns;
    }
}
