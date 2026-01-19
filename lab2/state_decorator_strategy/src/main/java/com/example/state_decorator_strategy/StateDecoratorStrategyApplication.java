package com.example.state_decorator_strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StateDecoratorStrategyApplication {

	public static void main(String[] args) {

		// ===== State Pattern =====
		System.out.println("=== STATE PATTERN ===");
		OrderContext context = new OrderContext();
		context.process(); // Mới tạo -> Đang xử lý
		context.process(); // Đang xử lý -> Đã giao
		context.process(); // Đã giao

		// ===== Strategy Pattern =====
		System.out.println("\n=== STRATEGY PATTERN ===");
		Order order = new Order();
		order.setShippingStrategy(new NormalShipping());
		order.shipOrder();

		order.setShippingStrategy(new ExpressShipping());
		order.shipOrder();

		// ===== Decorator Pattern =====
		System.out.println("\n=== DECORATOR PATTERN ===");
		OrderService service = new BasicOrderService();
		service = new InsuranceDecorator(service);
		service = new GiftWrapDecorator(service);

		service.process();
	}

}
