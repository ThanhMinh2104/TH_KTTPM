package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		
		// ========================================
		// DEMO 1: OBSERVER PATTERN
		// ========================================
		System.out.println("\n" + "=".repeat(100));
		System.out.println("DEMO 1: OBSERVER DESIGN PATTERN - STOCK MONITORING SYSTEM");
		System.out.println("=".repeat(100));

		Stock apple = new Stock("AAPL", 150);

		Investor a = new Investor("Nguyễn Văn A");
		Investor b = new Investor("Trần Thị B");

		apple.attach(a);
		apple.attach(b);

		apple.setPrice(155.5);
		apple.setPrice(165.0);
		
		System.out.println("=".repeat(100));
		System.out.println("OBSERVER PATTERN DEMO HOÀN TẤT");
		System.out.println("=".repeat(100));

		// ========================================
		// DEMO 2: ADAPTER PATTERN
		// ========================================
		System.out.println("\n" + "=".repeat(100));
		System.out.println("DEMO 2: ADAPTER DESIGN PATTERN - XML/JSON CONVERTER");
		System.out.println("=".repeat(100));

		// Tạo legacy XML system
		XmlDataSystem legacySystem = new XmlDataSystem("Banking Core System");
		
		// Tạo adapter để bridge JSON API với XML system
		XmlToJsonAdapter adapter = new XmlToJsonAdapter(legacySystem);
		
		// Tạo web service client (chỉ hiểu JSON)
		WebServiceClient webService = new WebServiceClient("User Management API", adapter);
		
		// Chạy demo
		webService.runSampleTest();
		
		System.out.println("=".repeat(100));
		System.out.println("ADAPTER PATTERN DEMO HOÀN TẤT");
		System.out.println("=".repeat(100));
	}
}
