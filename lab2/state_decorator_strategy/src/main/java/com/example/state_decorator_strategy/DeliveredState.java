package com.example.state_decorator_strategy;

public class DeliveredState implements OrderState {
    @Override
    public void handle(OrderContext order) {
        System.out.println("Đơn hàng đã được giao thành công.");
    }
}

