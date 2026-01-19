package com.example.state_decorator_strategy;

public class OrderContext {
    private OrderState state;

    public OrderContext() {
        this.state = new NewOrderState(); // ban đầu là Mới tạo
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public void process() {
        state.handle(this);
    }
}

