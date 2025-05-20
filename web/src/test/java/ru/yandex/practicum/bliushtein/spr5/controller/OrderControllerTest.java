package ru.yandex.practicum.bliushtein.spr5.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.XpathResultMatchers;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;

import javax.xml.xpath.XPathExpressionException;
import java.util.Optional;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.*;

//TODO uncomment tests when web tier will be reworked
@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = OrderController.class)
public class OrderControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    OrderService orderServiceMock;

/*    @Test
    void test_getOrder() throws Exception {
        testGetOrder("/order/{id}", false);
    }

    @Test
    void test_getNewOrder() throws Exception {
        testGetOrder("/order/{id}/new", true);
    }

    private void testGetOrder(String url, boolean isNewOrder) throws Exception {
        when(orderServiceMock.getOrder(ORDER_DTO.id())).thenReturn(Optional.of(ORDER_DTO));
        mockMvc.perform(get(url, ORDER_DTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("order"))
                .andExpect(model().attributeExists("order"))
                .andExpect(checkNewOrderMessageMatcher(isNewOrder))
                .andExpect(xpath("//h2[text() = \"Номер заказа: %s\"]", ORDER_DTO.id())
                        .exists())
                .andExpect(xpath("//b[text() = \"Название товара: %s\"]", ORDER_ITEM_DTO.name())
                        .exists())
                .andExpect(xpath("//b[text() = \"Описание товара: %s\"]", ORDER_ITEM_DTO.description())
                        .exists())
                .andExpect(xpath("//b[text() = \"Цена товара: %s\"]", ORDER_ITEM_DTO.price())
                        .exists())
                .andExpect(xpath("//b[text() = \"Количество: %s\"]", ORDER_ITEM_DTO.amount())
                        .exists())
                .andExpect(xpath("//h3[text() = \"Сумма заказа: %s\"]", ORDER_DTO.totalPrice())
                        .exists());
    }

    private ResultMatcher checkNewOrderMessageMatcher(boolean isNewOrder) throws XPathExpressionException {
        XpathResultMatchers xpathResultMatchers = xpath("//h1[text() = \"Поздравляем! Успешная покупка!\"]");
        return isNewOrder ? xpathResultMatchers.exists() : xpathResultMatchers.doesNotExist();
    }

    @Test
    void test_getOrders() throws Exception {
        when(orderServiceMock.getAllOrders()).thenReturn(List.of(ORDER_DTO));
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("orders"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(xpath("//a[text() = \"Номер заказа: %s\" and @href = \"/order/%s\"]", ORDER_DTO.id(), ORDER_DTO.id())
                        .exists())
                .andExpect(xpath("//b[text() = \"Название товара: %s\"]", ORDER_ITEM_DTO.name())
                        .exists())
                .andExpect(xpath("//b[text() = \"Цена товара: %s\"]", ORDER_ITEM_DTO.price())
                        .exists())
                .andExpect(xpath("//b[text() = \"Количество: %s\"]", ORDER_ITEM_DTO.amount())
                        .exists())
                .andExpect(xpath("//b[text() = \"Сумма заказа: %s\"]", ORDER_DTO.totalPrice())
                        .exists());
    }*/
}
