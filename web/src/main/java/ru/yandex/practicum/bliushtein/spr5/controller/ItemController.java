package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String showItem(Model model) {
        itemService.deleteAll();
        itemService.createItem("test item");
        itemService.findItemByName("test item").ifPresent(item -> model.addAttribute("item", item));
        return "item";
    }
}
