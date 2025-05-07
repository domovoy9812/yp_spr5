package ru.yandex.practicum.bliushtein.spr5.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.ItemSort;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.yandex.practicum.bliushtein.spr5.controller.TestData.ITEM_DTO;

@WebMvcTest(MainController.class)
@ContextConfiguration(classes = MainController.class)
public class MainControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ItemService itemServiceMock;

    @Test
    void test_searchItems() throws Exception {
        //when(itemServiceMock.searchItems("name 1", 0, 5, ItemSort.NO)).thenReturn(new PagedItemsDto(List.of(ITEM_DTO), true, true));
        when(itemServiceMock.searchItems(null, 0, 5, ItemSort.NO)).thenReturn(new PagedItemsDto(List.of(ITEM_DTO), true, true));
        mockMvc.perform(get("/main"))
                .andExpect(status().isOk());
    }
}
