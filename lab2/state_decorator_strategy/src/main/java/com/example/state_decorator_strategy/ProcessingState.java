package com.example.state_decorator_strategy;

public class ProcessingState implements OrderState {
    @Override
    public void handle(OrderContext order) {
        System.out.println("Đóng gói và vận chuyển...");
        order.setState(new DeliveredState());
    }
}
