package ru.yandex.practicum.bliushtein.spr5.data.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int amountInCart;

    private Long imageId;

    public Item() {
    }

    public Item(String name, String description, int price, int amountInCart) {
        this(null, name, description, price, amountInCart);
    }

    public Item(Long id, String name, String description, int price, int amountInCart) {
        this(id, name, description, price, amountInCart, null);
    }

    public Item(String name, String description, int price, int amountInCart, Long imageId) {
        this(null, name, description, price, amountInCart, imageId);
    }

    public Item(Long id, String name, String description, int price, int amountInCart, Long imageId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountInCart = amountInCart;
        this.id = id;
        this.imageId = imageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmountInCart() {
        return amountInCart;
    }

    public void setAmountInCart(int amountInCart) {
        this.amountInCart = amountInCart;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
