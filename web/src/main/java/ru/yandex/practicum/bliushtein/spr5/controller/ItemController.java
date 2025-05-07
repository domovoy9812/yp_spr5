package ru.yandex.practicum.bliushtein.spr5.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.ItemService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ItemDto;

import java.io.IOException;

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
    public String changeAmountInCart(HttpServletRequest request, @PathVariable("id") Long id, @RequestParam("action") String action) {
        switch (action) {
            case "plus" -> cartService.increaseAmountInCart(id);
            case "minus" -> cartService.decreaseAmountInCart(id);
            case "delete" -> cartService.removeFromCart(id);
            default -> throw new ShopException("Incorrect action value %s".formatted(action));
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @Value("classpath:image/default_image.png")
    Resource defaultImage;

    @GetMapping("/new")
    public String showNewItemPage() {
        return "new-item";
    }

    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createItem(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") int price,
                             @RequestPart("image") MultipartFile image) throws IOException {
        ItemDto item = itemService.createItem(name, description, price, image.getBytes());
        return "redirect:/item/" + item.id();
    }

    //TODO rework
    @GetMapping("/generate")
    public String generateItems() throws IOException {
        ItemDto item = null;
        for(int i = 1; i < 20; i++) {
            item = itemService.createItem("name %d".formatted(i), "description %d".formatted(i), 30 - i,
                    defaultImage.getContentAsByteArray());
        }
        return "redirect:/main";
    }
}
