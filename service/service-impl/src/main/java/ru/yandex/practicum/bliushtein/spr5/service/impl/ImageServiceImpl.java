package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;
import ru.yandex.practicum.bliushtein.spr5.data.repository.ImageRepository;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(@Autowired ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Mono<byte[]> getImage(Long id) {
        return imageRepository.findById(id)
                .switchIfEmpty(Mono.error(new ShopException("Image not found for id: %d".formatted(id))))
                .map(Image::getImage);
    }
}
