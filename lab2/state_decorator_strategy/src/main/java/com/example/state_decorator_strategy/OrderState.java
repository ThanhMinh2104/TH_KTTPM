package com.example.state_decorator_strategy;

public interface OrderState {
    void handle(OrderContext order);
}