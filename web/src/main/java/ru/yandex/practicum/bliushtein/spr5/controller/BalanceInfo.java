package ru.yandex.practicum.bliushtein.spr5.controller;

public record BalanceInfo(Integer value, boolean isError) {
    public static BalanceInfo ERROR = new BalanceInfo();
    public BalanceInfo(Integer value) {
        this(value, false);
    }
    private BalanceInfo() {
        this(null, true);
    }
}
