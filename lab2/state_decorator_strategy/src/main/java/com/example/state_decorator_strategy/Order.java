package com.example.state_decorator_strategy;

public class Order {
    private ShippingStrategy shippingStrategy;

    public void setShippingStrategy(ShippingStrategy strategy) {
        this.shippingStrategy = strategy;
    }

    public void shipOrder() {
        shippingStrategy.ship();
    }
}
