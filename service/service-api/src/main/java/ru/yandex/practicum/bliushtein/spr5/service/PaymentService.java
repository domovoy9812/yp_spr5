package ru.yandex.practicum.bliushtein.spr5.service;

import reactor.core.publisher.Mono;

public interface PaymentService {
    Mono<Integer> getBalance();
    Mono<Integer> pay(Integer price);
}
