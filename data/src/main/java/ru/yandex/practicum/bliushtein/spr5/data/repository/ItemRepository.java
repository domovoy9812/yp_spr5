package ru.yandex.practicum.bliushtein.spr5.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.data.entity.Item;

@Repository
public interface ItemRepository extends R2dbcRepository<Item, Long> {
    Flux<Item> findByAmountInCartGreaterThan(int value, Sort sort);
    @Modifying
    @Query("update items set amount_in_cart = 0 where amount_in_cart > 0")
    Mono<Integer> clearCart();
    default Flux<Item> findItemsInCart() {
        return findByAmountInCartGreaterThan(0, Sort.by("id"));
    }
    Flux<Item> findByNameLike(String name, Pageable pageable);
    /*Flux<Item> find(Pageable pageable);*/
}
