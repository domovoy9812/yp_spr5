package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.PagedItemsDto;

@Controller
@RequestMapping("/main")
public class MainController {
    private final ItemService itemService;

    public MainController(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String searchItems(Model model, @ModelAttribute ItemSearchParams sp) {
        PagedItemsDto items = itemService.searchItems(sp.getName(), sp.getPageNumber(), sp.getPageSize(), sp.getSort());
        model.addAttribute("itemsPage", items);
        model.addAttribute("searchParams", sp);
        return "main";
    }
}
