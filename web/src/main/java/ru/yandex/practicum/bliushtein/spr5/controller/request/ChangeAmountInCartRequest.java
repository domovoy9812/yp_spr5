package ru.yandex.practicum.bliushtein.spr5.controller.request;

public class ChangeAmountInCartRequest {
    private String action;

    public ChangeAmountInCartRequest() { }

    public ChangeAmountInCartRequest(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
