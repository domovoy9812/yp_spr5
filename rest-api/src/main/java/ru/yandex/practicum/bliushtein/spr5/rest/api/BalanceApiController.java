package ru.yandex.practicum.bliushtein.spr5.rest.api;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.yandex.practicum.bliushtein.spr5.rest.model.Balance;
import ru.yandex.practicum.bliushtein.spr5.rest.model.NotEnoughMoney;
import ru.yandex.practicum.bliushtein.spr5.rest.model.PayRequest;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-06-07T20:02:59.907656600+05:00[Asia/Qyzylorda]", comments = "Generator version: 7.12.0")
@Controller
@RequestMapping("${openapi.aPI.base-path:}")
public class BalanceApiController implements BalanceApi {
    private static final Integer DEFAULT_BALANCE = 100;
    @Override
    public Mono<ResponseEntity<Balance>> getBalance(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(new Balance(DEFAULT_BALANCE)));
    }

    @Override
    public Mono<ResponseEntity<Object>> pay(Mono<PayRequest> request, ServerWebExchange exchange) {
        return request.map(payRequest -> {
            int resultBalance = DEFAULT_BALANCE - payRequest.getPrice();
            if (resultBalance >= 0) {
                return ResponseEntity.ok(new Balance(resultBalance));
            } else {
                return ResponseEntity.badRequest().body(new NotEnoughMoney()
                        .requiredBalance(payRequest.getPrice())
                        .actualBalance(DEFAULT_BALANCE));
            }
        });
    }
}
