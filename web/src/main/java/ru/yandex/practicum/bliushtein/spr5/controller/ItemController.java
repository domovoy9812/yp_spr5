package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

@Controller
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final CartService cartService;

    public ItemController(@Autowired ItemService itemService, @Autowired CartService cartService) {
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public String showItem(Model model, @PathVariable("id") Long id) {
        itemService.findItemById(id).ifPresent(item -> model.addAttribute("item", item));
        return "item";
    }

    @PostMapping("/{id}/changeAmountInCart")
    public String changeAmountInCart(@PathVariable("id") Long id, @RequestParam("action") String action) {
        switch (action) {
            case "plus" -> cartService.increaseAmountInCart(id);
            case "minus" -> cartService.decreaseAmountInCart(id);
            default -> throw new ShopException("Incorrect action value %s".formatted(action));
        }
        return "redirect:/item/" + id;
    }

    //TODO rework
    @GetMapping("/generate")
    public String generateItems(Model model) {
        ItemDto item = null;
        for(int i = 1; i < 20; i++) {
            item = itemService.createItem("name %d".formatted(i), "description %d".formatted(i), i);
        }
        return "redirect:/item/%d".formatted(item.id());
    }
}
