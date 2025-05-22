package ru.yandex.practicum.bliushtein.spr5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import static org.mockito.Mockito.*;

public class ApplicationTest extends AbstractIntegrationTestWithTestcontainers {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoSpyBean
    ItemRepository itemRepository;
    @Value("classpath:image/default_image.png")
    Resource defaultImage;

    @Test
    void test_createAndGetItem() throws Exception {
        Long itemId = createItem();
        webTestClient.get().uri("/item/{id}", itemId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .xpath("//b[text() = \"Название товара: name\"]").exists()
                .xpath("//span[text() = \"Описание товара: description\"]").exists()
                .xpath("//b[text() = \"Цена товара: 100\"]").exists();

    }

    @Test
    void test_buy_error() throws Exception {
        Long itemId = createItem();
        when(itemRepository.clearCart()).thenReturn(Mono.error(new RuntimeException()));

        webTestClient.post().uri("/item/{id}/changeAmountInCart", itemId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Referer", "/main")
                .body(BodyInserters.fromFormData("action", "plus"))
                .exchange()
                .expectStatus().is3xxRedirection();
        webTestClient.post().uri("/cart/buy")
                .exchange()
                .expectStatus().is5xxServerError();
        webTestClient.get().uri("/orders")
                .exchange()
                .expectStatus().isOk()
                .expectBody().xpath("//tr/td").doesNotExist();
        /*mockMvc.perform(post("/item/{id}/changeAmountInCart", itemId)
                        .param("action", "plus"))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is5xxServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("exClass", RuntimeException.class.getName()));
        Object orders = mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andReturn().getModelAndView().getModel().get("orders");
        assertEquals(0, ((List) orders).size());*/
    }

    Long createItem() {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("image", defaultImage);
        bodyBuilder.part("name", "name");
        bodyBuilder.part("description", "description");
        bodyBuilder.part("price", "100");
        String redirectUrl = webTestClient.post().uri("/item/create")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .returnResult(Object.class)
                .getResponseHeaders().getFirst(HttpHeaders.LOCATION);
        return Long.valueOf(redirectUrl.substring(redirectUrl.lastIndexOf("/") + 1));
    }
}
