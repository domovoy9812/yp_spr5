package ru.yandex.practicum.bliushtein.spr5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.PaymentService;

import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.*;

@WebFluxTest(CartController.class)
@ContextConfiguration(classes = CartController.class)
public class CartControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    CartService cartServiceMock;

    @MockitoBean
    PaymentService paymentServiceMock;

    @Test
    void test_getCart() {
        when(cartServiceMock.getCart()).thenReturn(Mono.just(CART_DTO));
        webTestClient.get().uri("/cart").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody()
                .xpath("//b[text() = \"Название товара: %s\"]", ITEM_DTO_IN_CART_1.name()).exists()
                .xpath("//td[text() = \"Описание товара: %s\"]", ITEM_DTO_IN_CART_1.description()).exists()
                .xpath("//b[text() = \"Цена товара: %s\"]", ITEM_DTO_IN_CART_1.price()).exists()
                .xpath("//b[text() = \"Название товара: %s\"]", ITEM_DTO_IN_CART_2.name()).exists()
                .xpath("//td[text() = \"Описание товара: %s\"]", ITEM_DTO_IN_CART_2.description()).exists()
                .xpath("//b[text() = \"Цена товара: %s\"]", ITEM_DTO_IN_CART_2.price()).exists()
                .xpath("//b[text() = \"Суммарная стоимость: %s\"]", CART_DTO.totalPrice()).exists();
    }

    @Test
    void test_buy() {
        when(cartServiceMock.buy()).thenReturn(Mono.just(ORDER_ID));
        webTestClient.post().uri("/cart/buy").exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location("/order/" + ORDER_ID + "/new");
    }
}
