package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.PaymentService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.client.api.DefaultApi;
import ru.yandex.practicum.bliushtein.spr5.service.client.model.Balance;
import ru.yandex.practicum.bliushtein.spr5.service.client.model.NotEnoughMoney;
import ru.yandex.practicum.bliushtein.spr5.service.client.model.PayRequest;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final DefaultApi api;

    public PaymentServiceImpl(@Autowired DefaultApi api) {
        this.api = api;
    }

    @Override
    public Mono<Integer> getBalance() {
        //TODO избавиться от -1
        return api.getBalance().onErrorReturn(new Balance().balance(-1)).map(Balance::getBalance);
    }

    @Override
    public Mono<Integer> pay(Integer price) {
        return api.pay(new PayRequest().price(price))
                .doOnError(PaymentServiceImpl::handleException)
                .map(Balance::getBalance);
    }

    private static void handleException(Throwable e) {
        if (e instanceof WebClientResponseException.BadRequest br) {
            NotEnoughMoney response = br.getResponseBodyAs(NotEnoughMoney.class);
            throw ShopException.notEnoughMoney(response.getRequiredBalance(), response.getActualBalance());
        }
        throw new ShopException("Error during payment service call", e);
    }
}
