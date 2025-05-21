package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public Mono<Rendering> getOrder(Model model, @PathVariable("id") Long id) {
        Rendering rendering = Rendering.view("order")
                .modelAttribute("order", orderService.getOrder(id))
                .modelAttribute("isNewOrder", false)
                .build();
        return Mono.just(rendering);
    }

    @GetMapping("/order/{id}/new")
    public Mono<Rendering> getNewOrder(Model model, @PathVariable("id") Long id) {
        Rendering rendering = Rendering.view("order")
                .modelAttribute("order", orderService.getOrder(id))
                .modelAttribute("isNewOrder", true)
                .build();
        return Mono.just(rendering);
    }

    @GetMapping("/orders")
    public Mono<Rendering> getOrders() {
        return orderService.getAllOrders().collectList()
                .map(orders -> Rendering.view("orders")
                        .modelAttribute("orders", orders)
                        .build());
    }
}
