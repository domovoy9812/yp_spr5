package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.bliushtein.spr5.service.OrderService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.OrderDto;

import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(@Autowired OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{id}")
    public String getOrder(Model model, @PathVariable("id") Long id) {
        fillOrderInModel(model, id, false);
        return "order";
    }

    @GetMapping("/order/{id}/new")
    public String getNewOrder(Model model, @PathVariable("id") Long id) {
        fillOrderInModel(model, id, true);
        return "order";
    }

    private void fillOrderInModel(Model model, Long id, boolean isNewOrder) {
        orderService.getOrder(id).ifPresentOrElse(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("isNewOrder", isNewOrder);
                },
                () -> {
                    throw new ShopException("Order is not found. id: %d".formatted(id));
                });
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }
}
