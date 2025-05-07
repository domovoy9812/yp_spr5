package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
