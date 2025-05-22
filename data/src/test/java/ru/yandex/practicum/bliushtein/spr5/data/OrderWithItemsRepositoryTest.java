package ru.yandex.practicum.bliushtein.spr5.data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderItem;
import ru.yandex.practicum.bliushtein.spr5.data.entity.OrderWithItems;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderItemRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderRepository;
import ru.yandex.practicum.bliushtein.spr5.data.repository.OrderWithItemsRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.data.TestData.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OrderWithItemsRepository.class)
public class OrderWithItemsRepositoryTest {

    @Autowired
    OrderWithItemsRepository orderWithItemsRepository;

    @MockitoBean
    OrderItemRepository orderItemRepository;
    @MockitoBean
    OrderRepository orderRepository;

    @Test
    void test_save() {
        when(orderRepository.save(NOT_SAVED_ORDER)).thenReturn(Mono.just(SAVED_ORDER));
        when(orderItemRepository.saveAll(any(Iterable.class))).thenReturn(Flux.just(2L));
        Long orderId = orderWithItemsRepository.save(NOT_SAVED_ORDER, List.of(ITEM)).block();
        assertEquals(SAVED_ORDER.getId(), orderId);
        ArgumentCaptor<List<OrderItem>> orderItemsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(orderItemRepository).saveAll(orderItemsArgumentCaptor.capture());
        List<OrderItem> orderItems = orderItemsArgumentCaptor.getValue();
        assertEquals(1, orderItems.size());
        OrderItem orderItem = orderItems.getFirst();
        assertEquals(SAVED_ORDER.getId(), orderItem.getOrderId());
        assertEquals(ITEM.getId(), orderItem.getItemId());
        assertEquals(ITEM.getName(), orderItem.getItemName());
        assertEquals(ITEM.getDescription(), orderItem.getItemDescription());
        assertEquals(ITEM.getAmountInCart(), orderItem.getAmount());
        assertEquals(ITEM.getPrice(), orderItem.getPrice());
        assertEquals(ITEM.getImageId(), orderItem.getImageId());
    }

    @Test
    void test_findById() {
        when(orderRepository.findById(SAVED_ORDER.getId())).thenReturn(Mono.just(SAVED_ORDER));
        when(orderItemRepository.findByOrderId(SAVED_ORDER.getId())).thenReturn(Flux.just(SAVED_ORDER_ITEM));
        OrderWithItems orderWithItems = orderWithItemsRepository.findById(SAVED_ORDER.getId()).block();
        assertEquals(SAVED_ORDER, orderWithItems.order());
        assertEquals(1, orderWithItems.items().size());
        assertEquals(SAVED_ORDER_ITEM, orderWithItems.items().getFirst());
    }

    @Test
    void test_findAll() {
        when(orderRepository.findAll()).thenReturn(Flux.just(SAVED_ORDER));
        when(orderItemRepository.findByOrderId(SAVED_ORDER.getId())).thenReturn(Flux.just(SAVED_ORDER_ITEM));
        List<OrderWithItems> orderWithItemsList = orderWithItemsRepository.findAll().collectList().block();
        assertEquals(1, orderWithItemsList.size());
        OrderWithItems orderWithItems = orderWithItemsList.getFirst();
        assertEquals(SAVED_ORDER, orderWithItems.order());
        assertEquals(1, orderWithItems.items().size());
        assertEquals(SAVED_ORDER_ITEM, orderWithItems.items().getFirst());
    }
}
