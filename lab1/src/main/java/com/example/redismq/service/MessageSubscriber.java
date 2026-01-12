package com.example.redismq.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MessageSubscriber {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void onMessage(String message) {
        String timestamp = LocalDateTime.now().format(TIME_FORMATTER);
        
        // Chỉ hiển thị message từ người khác (không phải từ chính mình publish)
        System.out.println("\n[" + timestamp + "] " + message);
        System.out.print("> "); // Prompt cho người dùng
    }
}