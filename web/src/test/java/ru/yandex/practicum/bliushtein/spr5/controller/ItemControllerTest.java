package ru.yandex.practicum.bliushtein.spr5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;

import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.*;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ItemController.class)
@ContextConfiguration(classes = ItemController.class)
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ItemService itemServiceMock;

    @Test
    void test_showItem() throws Exception {
        when(itemServiceMock.findItemById(ITEM_DTO.id())).thenReturn(Optional.of(ITEM_DTO));
        mockMvc.perform(get("/item/{id}", ITEM_DTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("item"))
                .andExpect(model().attributeExists("item"))
                .andExpect(xpath("//b[text() = \"%s\"]", ITEM_DTO.name())
                        .exists())
                .andExpect(xpath("//span[text() = \"%s\"]", ITEM_DTO.description())
                .exists())
                .andExpect(xpath("//b[text() = \"%s\"]", ITEM_DTO.price())
                        .exists())
                .andExpect(xpath("//span[text() = \"%s\"]", ITEM_DTO.amountInCart())
                        .exists());
    }

    @Test
    void test_changeAmountInCart_plus() throws Exception {
        mockMvc.perform(post("/item/{id}/addToCart", ITEM_DTO.id())
                .param("action", "plus"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/item/" + ITEM_DTO.id()));
        verify(itemServiceMock).increaseAmountInCart(ITEM_DTO.id());
    }

    @Test
    void test_changeAmountInCart_minus() throws Exception {
        mockMvc.perform(post("/item/{id}/addToCart", ITEM_DTO.id())
                        .param("action", "minus"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/item/" + ITEM_DTO.id()));
        verify(itemServiceMock).decreaseAmountInCart(ITEM_DTO.id());
    }
}
