package com.example.state_decorator_strategy;

public class NormalShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("Giao hàng tiêu chuẩn.");
    }
}

