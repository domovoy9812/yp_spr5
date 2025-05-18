package ru.yandex.practicum.bliushtein.spr5.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "order_items")
public class OrderItem {
    @Id
    private Long id;
    private Long orderId;
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private int price;
    private int amount;
    private Long imageId;

    public OrderItem() {
    }

    public OrderItem(Long orderId, Item item) {
        this.orderId = orderId;
        this.itemId = item.getId();
        this.itemName = item.getName();
        this.itemDescription = item.getDescription();
        this.price = item.getPrice();
        this.amount = item.getAmountInCart();
        this.imageId = item.getImageId();
    }

    public OrderItem(Long id, Long orderId, Item item) {
        this(orderId, item);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
