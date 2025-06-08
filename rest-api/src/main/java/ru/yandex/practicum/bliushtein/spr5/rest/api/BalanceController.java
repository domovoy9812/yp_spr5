package ru.yandex.practicum.bliushtein.spr5.rest.api;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.rest.model.Response;
import ru.yandex.practicum.bliushtein.spr5.rest.model.PayRequest;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${openapi.aPI.base-path:}")
public class BalanceController implements BalanceApi {
    private static final Integer DEFAULT_BALANCE = 100;
    private static final String NOT_ENOUGH_MONEY_ERROR_MESSAGE = "Cant buy items. Not enough money. Required balance: %d. Actual balance: %d";

    @Override
    public Mono<ResponseEntity<Response>> getBalance(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(new Response(Response.TypeEnum.SUCCESS).balance(DEFAULT_BALANCE)));
    }

    @Override
    public Mono<ResponseEntity<Response>> pay(Mono<PayRequest> request, ServerWebExchange exchange) {
        return request.map(payRequest -> {
            int resultBalance = DEFAULT_BALANCE - payRequest.getPrice();
            if (resultBalance >= 0) {
                return ResponseEntity.ok(new Response(Response.TypeEnum.SUCCESS).balance(resultBalance));
            } else {
                return ResponseEntity.badRequest().body(new Response(Response.TypeEnum.ERROR)
                        .error(NOT_ENOUGH_MONEY_ERROR_MESSAGE.formatted(payRequest.getPrice(), DEFAULT_BALANCE)));
            }
        });
    }
}
