package ru.yandex.practicum.bliushtein.spr5.service;

import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<byte[]> getImage(Long id);
}
