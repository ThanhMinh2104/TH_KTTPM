package com.example.redismq;

import com.example.redismq.service.MessagePublisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleChatRunner implements CommandLineRunner {

    private final MessagePublisher messagePublisher;

    public ConsoleChatRunner(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    @Override
    public void run(String... args) throws Exception {
        // Đợi Spring Boot khởi động hoàn tất
        Thread.sleep(2000);
        
        System.out.println("\n");
        System.out.println("==============================================");
        System.out.println("       REDIS MQ CHAT APPLICATION");
        System.out.println("==============================================");
        System.out.println("Commands:");
        System.out.println("  - Type your message and press Enter to send");
        System.out.println("  - Type 'exit' or 'quit' to close");
        System.out.println("==============================================");
        System.out.println("\n");
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();
        
        if (username.isEmpty()) {
            username = "User" + System.currentTimeMillis() % 1000;
        }
        
        System.out.println("Welcome, " + username + "! Start chatting...\n");
        
        while (true) {
            System.out.print(username + " > ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                System.out.println("\nGoodbye! Closing chat...");
                System.exit(0);
                break;
            }
            
            if (!input.isEmpty()) {
                String message = String.format("[%s]: %s", username, input);
                messagePublisher.publish(message);
            }
        }
    }
}