package ru.yandex.practicum.bliushtein.spr5.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private int totalPrice;

    public Order() {}

    public Order(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Order(Long id, int totalPrice) {
        this(totalPrice);
        this.id = id;
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
