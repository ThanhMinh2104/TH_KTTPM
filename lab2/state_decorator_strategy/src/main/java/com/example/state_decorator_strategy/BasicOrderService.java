package com.example.state_decorator_strategy;

public class BasicOrderService implements OrderService {
    @Override
    public void process() {
        System.out.println("Xử lý đơn hàng cơ bản.");
    }
}

