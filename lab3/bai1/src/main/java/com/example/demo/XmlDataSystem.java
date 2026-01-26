package com.example.demo;

import java.time.LocalDateTime;
import java.util.regex.Pattern;


public class XmlDataSystem {
    
    private String systemName;
    
    public XmlDataSystem(String systemName) {
        this.systemName = systemName;
    }
    

    public String processXml(String xmlData) {
        System.out.println("[" + systemName + "] Đang xử lý dữ liệu XML...");
        
        if (!validateXml(xmlData)) {
            throw new IllegalArgumentException("Dữ liệu XML không hợp lệ!");
        }
        
        // Simulate XML processing
        String processedXml = xmlData
            .replace("<data>", "<processed_data timestamp=\"" + LocalDateTime.now() + "\">")
            .replace("</data>", "</processed_data>");
            
        System.out.println("[" + systemName + "] Xử lý XML hoàn tất!");
        return processedXml;
    }
    

    public boolean validateXml(String xmlData) {
        if (xmlData == null || xmlData.trim().isEmpty()) {
            return false;
        }
        
        // Simple XML validation
        return xmlData.trim().startsWith("<") && 
               xmlData.trim().endsWith(">") &&
               xmlData.contains("</");
    }
    

    public String getSystemInfo() {
        return "XML Data System: " + systemName + " (Legacy System - Only supports XML)";
    }
    

    public String getSampleXmlData() {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <data>
                <user>
                    <id>123</id>
                    <name>Nguyen Van A</name>
                    <email>nguyenvana@example.com</email>
                </user>
                <timestamp>2026-01-26T19:45:00</timestamp>
            </data>
            """;
    }
}