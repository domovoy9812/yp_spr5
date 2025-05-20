package ru.yandex.practicum.bliushtein.spr5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;

import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO uncomment tests when web tier will be reworked
@WebMvcTest(CartController.class)
@ContextConfiguration(classes = CartController.class)
public class CartControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CartService cartServiceMock;

/*    @Test
    void test_getCart() throws Exception {
        when(cartServiceMock.getCart()).thenReturn(CART_DTO);
        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(xpath("//b[text() = \"Название товара: %s\"]", ITEM_DTO_IN_CART_1.name())
                        .exists())
                .andExpect(xpath("//td[text() = \"Описание товара: %s\"]", ITEM_DTO_IN_CART_1.description())
                        .exists())
                .andExpect(xpath("//b[text() = \"Цена товара: %s\"]", ITEM_DTO_IN_CART_1.price())
                        .exists())
                .andExpect(xpath("//b[text() = \"Название товара: %s\"]", ITEM_DTO_IN_CART_2.name())
                        .exists())
                .andExpect(xpath("//td[text() = \"Описание товара: %s\"]", ITEM_DTO_IN_CART_2.description())
                        .exists())
                .andExpect(xpath("//b[text() = \"Цена товара: %s\"]", ITEM_DTO_IN_CART_2.price())
                        .exists())
                .andExpect(xpath("//b[text() = \"Суммарная стоимость: %s\"]", CART_DTO.totalPrice())
                        .exists());
    }

    @Test
    void test_buy() throws Exception {
        when(cartServiceMock.buy()).thenReturn(ORDER_ID);
        mockMvc.perform(post("/cart/buy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/" + ORDER_ID + "/new"));
    }*/
}
