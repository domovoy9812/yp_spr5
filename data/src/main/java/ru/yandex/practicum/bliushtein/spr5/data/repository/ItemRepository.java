package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByName(String name);
    List<Item> findByAmountInCartGreaterThan(int value);

    @Modifying
    @Query("update Item i set i.amountInCart = 0 where i.amountInCart > 0")
    void clearCart();

    default List<Item> findItemsInCart() {
        return findByAmountInCartGreaterThan(0);
    }
}
