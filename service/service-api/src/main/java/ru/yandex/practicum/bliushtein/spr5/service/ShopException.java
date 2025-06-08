package ru.yandex.practicum.bliushtein.spr5.service;

public class ShopException extends RuntimeException {
    private static final String ITEM_WITH_ID_D_IS_NOT_FOUND_ERROR_MESSAGE = "Item with id %d is not found";
    private static final String AMOUNT_IN_CART_CANT_BE_NEGATIVE_ERROR_MESSAGE = "Amount in cart cant be negative";
    private static final String CART_IS_EMPTY_ERROR_MESSAGE = "Can't buy! Cart is empty";
    private static final String PRICE_SHOULD_BE_POSITIVE_ERROR_MESSAGE = "Price should be positive. current price: %d";
    private static final String NOT_ENOUGH_MONEY_ERROR_MESSAGE = "Cant buy items. Not enough money. Required balance: %d. Actual balance: %d";

    public static ShopException itemNotFound(Long id) {
        return new ShopException(ITEM_WITH_ID_D_IS_NOT_FOUND_ERROR_MESSAGE.formatted(id));
    }

    public static ShopException amountInCartCantBeNegative() {
        return new ShopException(AMOUNT_IN_CART_CANT_BE_NEGATIVE_ERROR_MESSAGE);
    }

    public static ShopException cartIsEmpty() {
        throw new ShopException(CART_IS_EMPTY_ERROR_MESSAGE);
    }

    public static ShopException priceShouldBePositive(int price) {
        return new ShopException(PRICE_SHOULD_BE_POSITIVE_ERROR_MESSAGE.formatted(price));
    }

    public static ShopException notEnoughMoney(Integer requiredBalance, Integer actualBalance) {
        return new ShopException(NOT_ENOUGH_MONEY_ERROR_MESSAGE.formatted(requiredBalance, actualBalance));
    }

    public ShopException(String message) {
        super(message);
    }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }
}