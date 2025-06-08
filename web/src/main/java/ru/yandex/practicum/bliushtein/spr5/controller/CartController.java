package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.CartService;
import ru.yandex.practicum.bliushtein.spr5.service.PaymentService;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final PaymentService paymentService;
    public CartController(@Autowired CartService cartService,
                          @Autowired PaymentService paymentService) {
        this.cartService = cartService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public Mono<Rendering> getCart() {
        Rendering rendering = Rendering.view("cart")
                .modelAttribute("cart", cartService.getCart())
                .modelAttribute("balance", paymentService.getBalance())
                .build();
        return Mono.just(rendering);
    }

    @PostMapping("/buy")
    public Mono<Rendering> buy() {
        return cartService.buy()
                .map(orderId -> Rendering.redirectTo("/order/" + orderId + "/new").build());
    }

}
