package ru.yandex.practicum.bliushtein.spr5.service;

public class ShopException extends RuntimeException {
    private static final String ITEM_WITH_ID_D_IS_NOT_FOUND_ERROR_MESSAGE = "Item with id %d is not found";
    private static final String AMOUNT_IN_CART_CANT_BE_NEGATIVE_ERROR_MESSAGE = "Amount in cart cant be negative";

    public static void throwItemNotFound(Long id) {
        throw new ShopException(ITEM_WITH_ID_D_IS_NOT_FOUND_ERROR_MESSAGE.formatted(id));
    }
    public static void throwAmountInCartCantBeNegative() {
        throw new ShopException(AMOUNT_IN_CART_CANT_BE_NEGATIVE_ERROR_MESSAGE);
    }
    public ShopException(String message) {
        super(message);
    }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }

}