package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<Rendering> handleException(Exception exception) {
        Rendering rendering = Rendering.view("error")
                .modelAttribute("exClass", Mono.just(exception.getClass().getName()))
                .modelAttribute("exMessage", Mono.just(exception.getMessage()))
                .modelAttribute("exStackTrace", Mono.just(exception.getStackTrace())).build();
        return Mono.just(rendering);
    }
}
