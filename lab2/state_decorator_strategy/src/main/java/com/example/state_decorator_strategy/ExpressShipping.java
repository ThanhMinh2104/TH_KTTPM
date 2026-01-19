package com.example.state_decorator_strategy;

public class ExpressShipping implements ShippingStrategy {
    @Override
    public void ship() {
        System.out.println("Giao hàng nhanh (Express).");
    }
}

