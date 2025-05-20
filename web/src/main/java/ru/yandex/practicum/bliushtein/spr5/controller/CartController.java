package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.dto.CartDto;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(@Autowired CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String getCart(Model model) {
        loadCartToModel(model);
        return "cart";
    }

    @PostMapping("/buy")
    public String buy() {
        Long orderId = cartService.buy().block();
        return "redirect:/order/" + orderId + "/new";
    }

    private void loadCartToModel(Model model) {
        CartDto cart = cartService.getCart().block();
        model.addAttribute("cart", cart);
    }
}
