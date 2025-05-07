package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ImageRepository;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.dto.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(@Autowired ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public byte[] getImage(Long id) {
        return imageRepository.findById(id).map(Image::getImage)
                .orElseThrow(() -> new ShopException("Image not found for id: %d".formatted(id)));
    }
}
