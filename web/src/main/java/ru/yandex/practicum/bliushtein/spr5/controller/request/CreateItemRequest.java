package ru.yandex.practicum.bliushtein.spr5.controller.request;

import org.springframework.http.codec.multipart.FilePart;

public class CreateItemRequest {
    private String name;
    private String description;
    private int price;
    private FilePart image;

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

    public FilePart getImage() {
        return image;
    }

    public void setImage(FilePart image) {
        this.image = image;
    }
}
