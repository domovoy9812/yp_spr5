package ru.yandex.practicum.bliushtein.spr5.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private int totalPrice;

    public Order() {}

    public Order(Long id, int totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public Order(int totalPrice) {
        this(null, totalPrice);
    }

    public Order(List<Item> items) {
        this(items.stream().mapToInt(it -> it.getAmountInCart() * it.getPrice()).sum());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
