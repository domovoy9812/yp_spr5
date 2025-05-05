package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);
    List<Item> findByAmountInCartGreaterThan(int value);
    default List<Item> findItemsInCart() {
        return findByAmountInCartGreaterThan(0);
    }
}
