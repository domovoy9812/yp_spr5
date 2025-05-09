package ru.yandex.practicum.bliushtein.spr5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception, Model model) {
        model.addAttribute("exClass", exception.getClass().getName());
        model.addAttribute("exMessage", exception.getMessage());
        model.addAttribute("exStackTrace", exception.getStackTrace());
        return "error";
    }
}
