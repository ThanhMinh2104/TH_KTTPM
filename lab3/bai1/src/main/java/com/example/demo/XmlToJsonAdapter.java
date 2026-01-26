package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.regex.Pattern;

public class XmlToJsonAdapter implements DataProcessor {
    
    private XmlDataSystem xmlSystem;
    private ObjectMapper objectMapper;
    
    public XmlToJsonAdapter(XmlDataSystem xmlSystem) {
        this.xmlSystem = xmlSystem;
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public String processJson(String jsonData) {
        System.out.println("\n[ADAPTER] Bắt đầu chuyển đổi JSON → XML → JSON");
        
        try {
            // Bước 1: Validate JSON input
            if (!validateJson(jsonData)) {
                throw new IllegalArgumentException("Dữ liệu JSON đầu vào không hợp lệ!");
            }
            
            // Bước 2: Chuyển đổi JSON → XML
            System.out.println("[ADAPTER] Chuyển đổi JSON sang XML...");
            String xmlData = convertJsonToXml(jsonData);
            System.out.println("[ADAPTER] Chuyển đổi JSON → XML thành công");
            
            // Bước 3: Gọi hệ thống XML xử lý
            String processedXml = xmlSystem.processXml(xmlData);
            
            // Bước 4: Chuyển đổi XML → JSON
            System.out.println("[ADAPTER] Chuyển đổi XML sang JSON...");
            String resultJson = convertXmlToJson(processedXml);
            System.out.println("[ADAPTER] Chuyển đổi XML → JSON thành công");
            
            System.out.println("[ADAPTER] Hoàn tất quá trình chuyển đổi!\n");
            return resultJson;
            
        } catch (Exception e) {
            System.err.println("[ADAPTER] Lỗi trong quá trình chuyển đổi: " + e.getMessage());
            throw new RuntimeException("Adapter processing failed", e);
        }
    }
    
    @Override
    public boolean validateJson(String jsonData) {
        if (jsonData == null || jsonData.trim().isEmpty()) {
            return false;
        }
        
        try {
            objectMapper.readTree(jsonData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String getProcessorInfo() {
        return "JSON-XML Adapter (Bridges JSON API with " + xmlSystem.getSystemInfo() + ")";
    }

    private String convertJsonToXml(String jsonData) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            
            // Simple JSON to XML conversion
            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<data>\n");
            
            // Convert JSON fields to XML elements
            jsonNode.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                
                if (value.isObject()) {
                    xml.append("  <").append(key).append(">\n");
                    value.fields().forEachRemaining(subEntry -> {
                        xml.append("    <").append(subEntry.getKey()).append(">")
                           .append(subEntry.getValue().asText())
                           .append("</").append(subEntry.getKey()).append(">\n");
                    });
                    xml.append("  </").append(key).append(">\n");
                } else {
                    xml.append("  <").append(key).append(">")
                       .append(value.asText())
                       .append("</").append(key).append(">\n");
                }
            });
            
            xml.append("</data>");
            return xml.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert JSON to XML", e);
        }
    }

    private String convertXmlToJson(String xmlData) {
        try {
            // Simple XML to JSON conversion
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            
            // Extract data between tags (simplified parsing)
            String[] lines = xmlData.split("\n");
            boolean firstField = true;
            
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("<") && line.endsWith(">") && 
                    !line.startsWith("<?") && !line.startsWith("</") &&
                    !line.equals("<data>") && !line.equals("</data>") &&
                    !line.equals("<processed_data>") && !line.contains("processed_data")) {
                    
                    // Extract tag name and value
                    int startTag = line.indexOf('<') + 1;
                    int endTag = line.indexOf('>');
                    int startEndTag = line.indexOf("</");
                    
                    if (startTag > 0 && endTag > startTag && startEndTag > endTag) {
                        String tagName = line.substring(startTag, endTag);
                        String value = line.substring(endTag + 1, startEndTag);
                        
                        if (!firstField) {
                            json.append(",\n");
                        }
                        json.append("  \"").append(tagName).append("\": \"").append(value).append("\"");
                        firstField = false;
                    }
                }
            }
            
            json.append("\n}");
            return json.toString();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert XML to JSON", e);
        }
    }
}