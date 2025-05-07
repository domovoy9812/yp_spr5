package ru.yandex.practicum.bliushtein.spr5.service;

public class ShopException extends RuntimeException {
    private static final String ITEM_WITH_ID_D_IS_NOT_FOUND_ERROR_MESSAGE = "Item with id %d is not found";
    private static final String AMOUNT_IN_CART_CANT_BE_NEGATIVE_ERROR_MESSAGE = "Amount in cart cant be negative";
    private static final String CART_IS_EMPTY_ERROR_MESSAGE = "Can't buy! Cart is empty";
    private static final String PRICE_SHOULD_BE_POSITIVE_ERROR_MESSAGE = "Price should be positive. current price: %d";

    public static void throwItemNotFound(Long id) {
        throw new ShopException(ITEM_WITH_ID_D_IS_NOT_FOUND_ERROR_MESSAGE.formatted(id));
    }
    public static void throwAmountInCartCantBeNegative() {
        throw new ShopException(AMOUNT_IN_CART_CANT_BE_NEGATIVE_ERROR_MESSAGE);
    }
    public static void throwCartIsEmpty() {
        throw new ShopException(CART_IS_EMPTY_ERROR_MESSAGE);
    }
    public ShopException(String message) {
        super(message);
    }

    public static void throwPriceShouldBePositive(int price) {
        throw new ShopException(PRICE_SHOULD_BE_POSITIVE_ERROR_MESSAGE.formatted(price));
    }

}