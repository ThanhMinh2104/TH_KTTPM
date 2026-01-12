package com.example.redismq.controller;

import com.example.redismq.service.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private MessagePublisher messagePublisher;

    @PostMapping("/send")
    public Map<String, Object> sendMessage(@RequestBody Map<String, String> request) {
        String username = request.getOrDefault("username", "Anonymous");
        String message = request.get("message");
        
        if (message == null || message.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("error", "Message cannot be empty");
            return error;
        }
        
        // Format: [username] message
        String formattedMessage = String.format("[%s] %s", username, message);
        messagePublisher.publish(formattedMessage);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Message sent successfully");
        response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return response;
    }
    
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "online");
        status.put("service", "Redis MQ Chat");
        status.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return status;
    }
}