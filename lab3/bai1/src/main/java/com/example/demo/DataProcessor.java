package com.example.demo;

/**
 * Target interface - Định nghĩa interface mà Client mong đợi
 * Client muốn xử lý dữ liệu JSON
 */
public interface DataProcessor {
    
    /**
     * Xử lý dữ liệu JSON và trả về kết quả JSON
     */
    String processJson(String jsonData);
    
    /**
     * Validate dữ liệu JSON
     */
    boolean validateJson(String jsonData);
    
    /**
     * Lấy thông tin về processor
     */
    String getProcessorInfo();
}