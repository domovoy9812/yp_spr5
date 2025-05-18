package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;

@Repository
public interface ImageRepository extends ReactiveCrudRepository<Image, Long> {
}
