package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Order;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderWithItemsRepository;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.PaymentService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;
import ru.yandex.practicum.bliushtein.spr5.service.mapper.ItemMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.service.impl.TestData.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {CartServiceImpl.class, ItemMapper.class})
public class CartServiceImplTest {
    @Autowired
    CartService cartService;

    @MockitoBean
    ItemRepository itemRepositoryMock;

    @MockitoBean
    PaymentService paymentServiceMock;

    @MockitoBean
    OrderWithItemsRepository orderRepositoryMock;


    @Test
    void test_increaseAmountInCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepositoryMock.findById(item.getId())).thenReturn(Mono.just(item));
        when(itemRepositoryMock.save(any())).thenReturn(Mono.just(item));
        cartService.increaseAmountInCart(ITEM_1.getId()).block();
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryMock).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_1.getAmountInCart() + 1, storedItem.getAmountInCart());
    }

    @Test
    void test_increaseAmountInCart_ItemNotFound() {
        when(itemRepositoryMock.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Mono.empty());
        assertThrows(ShopException.class, () -> cartService.increaseAmountInCart(NOT_EXISTING_ITEM_ID).block());
    }

    @Test
    void test_increaseAmountInCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> cartService.increaseAmountInCart(null).block());
        verify(itemRepositoryMock, never()).findById(any(Long.class));
    }

    @Test
    void test_decreaseAmountInCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepositoryMock.findById(item.getId())).thenReturn(Mono.just(item));
        when(itemRepositoryMock.save(any())).thenReturn(Mono.just(item));
        cartService.decreaseAmountInCart(ITEM_1.getId()).block();
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryMock).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_1.getAmountInCart() - 1, storedItem.getAmountInCart());
    }

    @Test
    void test_decreaseAmountInCart_ItemNotInCart() {
        when(itemRepositoryMock.findById(ITEM_NOT_IN_CART.getId())).thenReturn(Mono.just(ITEM_NOT_IN_CART));
        assertThrows(ShopException.class, () -> cartService.decreaseAmountInCart(ITEM_NOT_IN_CART.getId()).block());
        verify(itemRepositoryMock, never()).save(any());
    }

    @Test
    void test_decreaseAmountInCart_ItemNotFound() {
        when(itemRepositoryMock.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Mono.empty());
        assertThrows(ShopException.class, () -> cartService.decreaseAmountInCart(NOT_EXISTING_ITEM_ID).block());
    }

    @Test
    void test_decreaseAmountInCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> cartService.decreaseAmountInCart(null).block());
        verify(itemRepositoryMock, never()).findById(any(Long.class));
    }

    @Test
    void test_removeFromCart() {
        Item item = copyItem(ITEM_1);
        when(itemRepositoryMock.findById(item.getId())).thenReturn(Mono.just(item));
        when(itemRepositoryMock.save(any())).thenReturn(Mono.just(item));
        cartService.removeFromCart(ITEM_1.getId()).block();
        ArgumentCaptor<Item> itemCaptor = ArgumentCaptor.forClass(Item.class);
        verify(itemRepositoryMock).save(itemCaptor.capture());
        Item storedItem = itemCaptor.getValue();
        assertEquals(ITEM_1.getId(), storedItem.getId());
        assertEquals(ITEM_AMOUNT_IN_CART_0, storedItem.getAmountInCart());
    }

    @Test
    void test_removeFromCart_ItemNotFound() {
        when(itemRepositoryMock.findById(NOT_EXISTING_ITEM_ID)).thenReturn(Mono.empty());
        assertThrows(ShopException.class, () -> cartService.removeFromCart(NOT_EXISTING_ITEM_ID).block());
    }

    @Test
    void test_removeFromCart_NullItemId() {
        assertThrows(NullPointerException.class, () -> cartService.removeFromCart(null).block());
        verify(itemRepositoryMock, never()).findById(any(Long.class));
    }

    @Test
    void test_getCart() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(Flux.just(ITEM_1, ITEM_2));
        CartDto cart = cartService.getCart().block();
        assertEquals(ITEM_1_AND_ITEM_2_TOTAL_PRICE, cart.totalPrice());
        assertTrue(cart.items().stream().map(ItemDto::id).toList().containsAll(List.of(ITEM_1.getId(), ITEM_2.getId())));
    }

    @Test
    void test_buy() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(Flux.just(ITEM_1));
        when(paymentServiceMock.pay(any())).thenReturn(Mono.just(0));
        when(orderRepositoryMock.save(any(), any())).thenReturn(Mono.just(CREATED_ORDER.getId()));
        when(itemRepositoryMock.clearCart()).thenReturn(Mono.just(1));
        Long orderId = cartService.buy().block();
        assertEquals(CREATED_ORDER.getId(), orderId);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        ArgumentCaptor<List<Item>> itemsCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderRepositoryMock).save(orderCaptor.capture(), itemsCaptor.capture());
        Order order = orderCaptor.getValue();
        List<Item> items = itemsCaptor.getValue();
        assertEquals(ITEM_1_TOTAL_PRICE, order.getTotalPrice());
        assertEquals(1, items.size());
        assertEquals(ITEM_1, items.getFirst());
        verify(itemRepositoryMock).clearCart();
    }

    @Test
    void test_buy_payment_error() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(Flux.just(ITEM_1));
        when(paymentServiceMock.pay(any())).thenReturn(Mono.error(new ShopException("test error message")));
        assertThrows(ShopException.class, () -> cartService.buy().block());
        verify(orderRepositoryMock, never()).save(any(), any());
        verify(itemRepositoryMock, never()).clearCart();
    }

    @Test
    void test_buy_emptyCart() {
        when(itemRepositoryMock.findItemsInCart())
                .thenReturn(Flux.empty());
        assertThrows(ShopException.class, () -> cartService.buy().block());
        verify(orderRepositoryMock, never()).save(any(), any());
        verify(itemRepositoryMock, never()).clearCart();
    }

}
