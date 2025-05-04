package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
}
