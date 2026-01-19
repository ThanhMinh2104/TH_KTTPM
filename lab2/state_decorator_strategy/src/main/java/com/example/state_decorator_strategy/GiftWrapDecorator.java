package com.example.state_decorator_strategy;

public class GiftWrapDecorator extends OrderDecorator {
    public GiftWrapDecorator(OrderService orderService) {
        super(orderService);
    }

    @Override
    public void process() {
        super.process();
        System.out.println("Gói quà cho đơn hàng.");
    }
}

