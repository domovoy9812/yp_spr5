package ru.yandex.practicum.bliushtein.spr5.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;

import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.*;

@WebFluxTest(OrderController.class)
@ContextConfiguration(classes = OrderController.class)
public class OrderControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    OrderService orderServiceMock;

    @Test
    void test_getOrder() throws Exception {
        testGetOrder("/order/{id}", false);
    }

    @Test
    void test_getNewOrder() throws Exception {
        testGetOrder("/order/{id}/new", true);
    }

    private void testGetOrder(String url, boolean isNewOrder) {
        when(orderServiceMock.getOrder(ORDER_DTO.id())).thenReturn(Mono.just(ORDER_DTO));
        webTestClient.get().uri(url, ORDER_DTO.id()).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody()
                .xpath("//h2[text() = \"Номер заказа: %s\"]", ORDER_DTO.id()).exists()
                .xpath("//b[text() = \"Название товара: %s\"]", ORDER_ITEM_DTO.name()).exists()
                .xpath("//b[text() = \"Описание товара: %s\"]", ORDER_ITEM_DTO.description()).exists()
                .xpath("//b[text() = \"Цена товара: %s\"]", ORDER_ITEM_DTO.price()).exists()
                .xpath("//b[text() = \"Количество: %s\"]", ORDER_ITEM_DTO.amount()).exists()
                .xpath("//h3[text() = \"Сумма заказа: %s\"]", ORDER_DTO.totalPrice()).exists()
                .xpath("//h1[text() = \"Поздравляем! Успешная покупка!\"]").nodeCount(isNewOrder ? 1 : 0);
    }

    @Test
    void test_getOrders() {
        when(orderServiceMock.getAllOrders()).thenReturn(Flux.just(ORDER_DTO));
        webTestClient.get().uri("/orders").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody()
                .xpath("//a[text() = \"Номер заказа: %s\" and @href = \"/order/%s\"]", ORDER_DTO.id(), ORDER_DTO.id()).exists()
                .xpath("//b[text() = \"Название товара: %s\"]", ORDER_ITEM_DTO.name()).exists()
                .xpath("//b[text() = \"Цена товара: %s\"]", ORDER_ITEM_DTO.price()).exists()
                .xpath("//b[text() = \"Количество: %s\"]", ORDER_ITEM_DTO.amount()).exists()
                .xpath("//b[text() = \"Сумма заказа: %s\"]", ORDER_DTO.totalPrice()).exists();
    }
}
