package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);
}
