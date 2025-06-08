package ru.yandex.practicum.bliushtein.spr5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.service.PaymentService;
import ru.yandex.practicum.bliushtein.spr5.service.ShopException;
import ru.yandex.practicum.bliushtein.spr5.service.client.api.DefaultApi;
import ru.yandex.practicum.bliushtein.spr5.service.client.model.Response;
import ru.yandex.practicum.bliushtein.spr5.service.client.model.PayRequest;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final DefaultApi api;

    public PaymentServiceImpl(@Autowired DefaultApi api) {
        this.api = api;
    }

    @Override
    public Mono<Integer> getBalance() {
        return api.getBalance().doOnError(PaymentServiceImpl::mapException).map(Response::getBalance);
    }

    @Override
    public Mono<Integer> pay(Integer price) {
        return api.pay(new PayRequest().price(price))
                .onErrorMap(PaymentServiceImpl::mapException)
                .map(Response::getBalance);
    }

    private static ShopException mapException(Throwable e) {
        if (e instanceof WebClientResponseException wcrException) {
            Response response = wcrException.getResponseBodyAs(Response.class);
            return new ShopException("Error during payment service call. " + response.getError(), e);
        }
        return new ShopException("Error during payment service call", e);
    }
}
