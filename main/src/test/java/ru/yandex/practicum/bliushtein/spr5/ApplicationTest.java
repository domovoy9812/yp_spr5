package ru.yandex.practicum.bliushtein.spr5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ItemRepository;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApplicationTest extends AbstractIntegrationTestWithTestcontainers {

    @Autowired
    MockMvc mockMvc;
    @MockitoSpyBean
    ItemRepository itemRepository;
    @Value("classpath:image/default_image.png")
    Resource defaultImage;

    @Test
    void test_createAndGetItem() throws Exception {
        Long itemId = createItem();
        mockMvc.perform(get("/item/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"));
    }

    @Test
    void test_buy_error() throws Exception {
        Long itemId = createItem();
        doThrow(RuntimeException.class).when(itemRepository).clearCart();
        mockMvc.perform(post("/item/{id}/changeAmountInCart", itemId)
                        .param("action", "plus"))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is5xxServerError())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("exClass", RuntimeException.class.getName()));
        Object orders = mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andReturn().getModelAndView().getModel().get("orders");
        assertEquals(0, ((List) orders).size());
    }

    Long createItem() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "image",
                "default_image.png",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                defaultImage.getContentAsByteArray()
        );
        String redirectUrl = mockMvc.perform(multipart("/item/create")
                .file(file)
                .param("name", "name")
                .param("description", "description")
                .param("price", "100"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().getRedirectedUrl();
        return Long.valueOf(redirectUrl.substring(redirectUrl.lastIndexOf("/") + 1));
    }
}
