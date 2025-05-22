package ru.yandex.practicum.bliushtein.spr5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.ItemSort;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

import java.util.List;

import static org.mockito.Mockito.when;
import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.ITEM_DTO;

@WebFluxTest(MainController.class)
@ContextConfiguration(classes = MainController.class)
public class MainControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    ItemService itemServiceMock;

    @Test
    void test_searchItems() {
        when(itemServiceMock.searchItems(null, 0, 5, ItemSort.NO))
                .thenReturn(Mono.just(new PagedItemsDto(List.of(ITEM_DTO), true, true)));
        webTestClient.get().uri("/main").exchange()
                .expectStatus().isOk()
                .expectBody()
                .xpath("//b[text() = \"Название: %s\"]", ITEM_DTO.name()).exists();
    }
}
