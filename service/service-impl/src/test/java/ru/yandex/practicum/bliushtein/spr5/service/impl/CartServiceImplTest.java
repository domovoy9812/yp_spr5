package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.service.impl.TestData.*;

//TODO uncomment tests when service tier will be reworked
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {CartServiceImpl.class, ItemMapper.class})
public class CartServiceImplTest {
    @Autowired
    CartService cartService;

    @MockitoBean
    ItemRepository itemRepositoryMock;

    @MockitoBean
    OrderRepository orderRepositoryMock;

/*
    @Test
    void test_increaseAmountInCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepositoryMock.findById(item.getId())).thenReturn(Optional.of(item));
        cartService.increaseAmountInCart(ITEM_1.getId());
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryMock).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_1.getAmountInCart() + 1, storedItem.getAmountInCart());
    }

    @Test
    void test_increaseAmountInCart_ItemNotFound() {
        when(itemRepositoryMock.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Optional.empty());
        assertThrows(ShopException.class, () -> cartService.increaseAmountInCart(NOT_EXISTING_ITEM_ID));
    }

    @Test
    void test_increaseAmountInCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> cartService.increaseAmountInCart(null));
        verify(itemRepositoryMock, never()).findById(any());
    }

    @Test
    void test_decreaseAmountInCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepositoryMock.findById(item.getId())).thenReturn(Optional.of(item));
        cartService.decreaseAmountInCart(ITEM_1.getId());
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryMock).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_1.getAmountInCart() - 1, storedItem.getAmountInCart());
    }

    @Test
    void test_decreaseAmountInCart_ItemNotInCart() {
        when(itemRepositoryMock.findById(ITEM_NOT_IN_CART.getId())).thenReturn(Optional.of(ITEM_NOT_IN_CART));
        assertThrows(ShopException.class, () -> cartService.decreaseAmountInCart(ITEM_NOT_IN_CART.getId()));
        verify(itemRepositoryMock, never()).save(any());
    }

    @Test
    void test_decreaseAmountInCart_ItemNotFound() {
        when(itemRepositoryMock.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Optional.empty());
        assertThrows(ShopException.class, () -> cartService.decreaseAmountInCart(NOT_EXISTING_ITEM_ID));
    }

    @Test
    void test_decreaseAmountInCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> cartService.decreaseAmountInCart(null));
        verify(itemRepositoryMock, never()).findById(any());
    }

    @Test
    void test_removeFromCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepositoryMock.findById(item.getId())).thenReturn(Optional.of(item));
        cartService.removeFromCart(ITEM_1.getId());
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryMock).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_AMOUNT_IN_CART_0, storedItem.getAmountInCart());
    }

    @Test
    void test_removeFromCart_ItemNotFound() {
        when(itemRepositoryMock.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Optional.empty());
        assertThrows(ShopException.class, () -> cartService.removeFromCart(NOT_EXISTING_ITEM_ID));
    }

    @Test
    void test_removeFromCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> cartService.removeFromCart(null));
        verify(itemRepositoryMock, never()).findById(any());
    }

    @Test
    void test_getCart() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(List.of(ITEM_1, ITEM_2));
        CartDto cart = cartService.getCart();
        assertEquals(ITEM_1_AND_ITEM_2_TOTAL_PRICE, cart.totalPrice());
        assertTrue(cart.items().stream().map(ItemDto::id).toList().containsAll(List.of(ITEM_1.getId(), ITEM_2.getId())));
    }

    @Test
    void test_buy() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(List.of(ITEM_1));
        when(orderRepositoryMock.save(any())).thenReturn(CREATED_ORDER);
        Long orderId = cartService.buy();
        assertEquals(CREATED_ORDER.getId(), orderId);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepositoryMock).save(orderCaptor.capture());
        Order order = orderCaptor.getValue();
        assertEquals(ITEM_1_TOTAL_PRICE, order.getTotalPrice());
        assertEquals(1, order.getOrderItems().size());
        OrderItem orderItem = order.getOrderItems().getFirst();
        assertEquals(ITEM_1.getId(), orderItem.getItemId());
        assertEquals(ITEM_1.getName(), orderItem.getItemName());
        assertEquals(ITEM_1.getDescription(), orderItem.getItemDescription());
        assertEquals(ITEM_1.getPrice(), orderItem.getPrice());
        assertEquals(ITEM_1.getAmountInCart(), orderItem.getAmount());
        verify(itemRepositoryMock).clearCart();
    }

    @Test
    void test_buy_emptyCart() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(List.of());
        assertThrows(ShopException.class, () -> cartService.buy());
        verify(orderRepositoryMock, never()).save(any());
        verify(itemRepositoryMock, never()).clearCart();
    }
 */
}
