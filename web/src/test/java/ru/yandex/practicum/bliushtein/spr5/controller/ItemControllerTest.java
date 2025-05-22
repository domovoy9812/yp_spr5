package ru.yandex.practicum.bliushtein.spr5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;

import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.*;

import static org.mockito.Mockito.*;

@WebFluxTest(ItemController.class)
@ContextConfiguration(classes = ItemController.class)
public class ItemControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    ItemService itemServiceMock;

    @MockitoBean
    CartService cartServiceMock;

    @Test
    void test_showItem() {
        when(itemServiceMock.findItemById(ITEM_DTO.id())).thenReturn(Mono.just(ITEM_DTO));
        webTestClient.get().uri("/item/{id}", ITEM_DTO.id()).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML)
                .expectBody()
                .xpath("//b[text() = \"Название товара: %s\"]", ITEM_DTO.name()).exists()
                .xpath("//span[text() = \"Описание товара: %s\"]", ITEM_DTO.description()).exists()
                .xpath("//b[text() = \"Цена товара: %s\"]", ITEM_DTO.price()).exists()
                .xpath("//span[text() = \"%s\"]", ITEM_DTO.amountInCart()).exists();
    }

    @Test
    void test_changeAmountInCart_plus() {
        when(cartServiceMock.increaseAmountInCart(ITEM_DTO.id())).thenReturn(Mono.empty());
        webTestClient.post().uri("/item/{id}/changeAmountInCart", ITEM_DTO.id())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Referer", REFERER_URI)
                .body(BodyInserters.fromFormData("action", "plus"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location(REFERER_URI);
        verify(cartServiceMock).increaseAmountInCart(ITEM_DTO.id());
    }

    @Test
    void test_changeAmountInCart_minus() {
        when(cartServiceMock.decreaseAmountInCart(ITEM_DTO.id())).thenReturn(Mono.empty());
        webTestClient.post().uri("/item/{id}/changeAmountInCart", ITEM_DTO.id())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Referer", REFERER_URI)
                .body(BodyInserters.fromFormData("action", "minus"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location(REFERER_URI);
        verify(cartServiceMock).decreaseAmountInCart(ITEM_DTO.id());
    }

    @Test
    void test_changeAmountInCart_delete() {
        when(cartServiceMock.removeFromCart(ITEM_DTO.id())).thenReturn(Mono.empty());
        webTestClient.post().uri("/item/{id}/changeAmountInCart", ITEM_DTO.id())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Referer", REFERER_URI)
                .body(BodyInserters.fromFormData("action", "delete"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location(REFERER_URI);
        verify(cartServiceMock).removeFromCart(ITEM_DTO.id());
    }
}
