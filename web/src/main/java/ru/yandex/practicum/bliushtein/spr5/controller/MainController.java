package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.controller.request.ItemSearchParams;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;

@Controller
@RequestMapping("/main")
public class MainController {
    private final ItemService itemService;

    public MainController(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public Mono<Rendering> searchItems(ItemSearchParams sp) {
        return itemService.searchItems(sp.getName(), sp.getPageNumber(), sp.getPageSize(), sp.getSort())
                .map(page -> Rendering.view("main")
                        .modelAttribute("itemsPage", page)
                        .modelAttribute("searchParams", sp)
                        .build());
    }
}
