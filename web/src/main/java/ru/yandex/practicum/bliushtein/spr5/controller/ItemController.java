package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(@Autowired ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public String showItem(Model model, @PathVariable("id") Long id) {
        itemService.findItemById(id).ifPresent(item -> model.addAttribute("item", item));
        return "item";
    }

    @PostMapping("/{id}/addToCart")
    public String changeAmountInCart(Model model, @PathVariable("id") Long id, @RequestParam("action") String action) {
        if ("plus".equals(action)) {
            itemService.increaseAmountInCart(id);
        } else if ("minus".equals(action)) {
            itemService.decreaseAmountInCart(id);
        } else throw new ShopException("Incorrect action value %s".formatted(action));
        return "redirect:/item/" + id;
    }

    //TODO rework
    @GetMapping("/generate")
    public String generateItems(Model model) {
        ItemDto item = null;
        for(int i = 0; i < 20; i++) {
            item = itemService.createItem("name %d".formatted(i), "description %d".formatted(i), i);
        }
        return "redirect:/item/%d".formatted(item.id());
    }
}
