package ru.yandex.practicum.bliushtein.spr5.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("images")
public class Image {
    @Id
    private Long id;

    private byte[] image;

    public Image() {
    }

    public Image(byte[] image) {
        this.image = image;
    }

    public Image(Long id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
