package com.example.demo;


public class WebServiceClient {
    
    private DataProcessor dataProcessor;
    private String serviceName;
    
    public WebServiceClient(String serviceName, DataProcessor dataProcessor) {
        this.serviceName = serviceName;
        this.dataProcessor = dataProcessor;
    }
    

    public void handleRequest(String jsonRequest) {
        System.out.println("[" + serviceName + "] Nhận request JSON:");
        System.out.println("" + jsonRequest);
        
        try {
            // Client chỉ biết về DataProcessor interface, không biết implementation
            String result = dataProcessor.processJson(jsonRequest);
            
            System.out.println("[" + serviceName + "] Kết quả xử lý:");
            System.out.println("" + result);
            
        } catch (Exception e) {
            System.err.println("[" + serviceName + "] Lỗi xử lý request: " + e.getMessage());
        }
    }
    

    public void showProcessorInfo() {
        System.out.println("[" + serviceName + "] Processor: " + dataProcessor.getProcessorInfo());
    }
    

    public void runSampleTest() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("[" + serviceName + "] CHẠY TEST ADAPTER PATTERN");
        System.out.println("=".repeat(80));
        
        showProcessorInfo();
        
        // Sample JSON data
        String sampleJson = """
            {
                "user": {
                    "id": "456",
                    "name": "Tran Thi B",
                    "email": "tranthib@example.com"
                },
                "action": "update_profile",
                "timestamp": "2026-01-26T20:00:00"
            }
            """;
            
        handleRequest(sampleJson);
        
        System.out.println("=".repeat(80));
        System.out.println("[" + serviceName + "] TEST HOÀN TẤT");
        System.out.println("=".repeat(80) + "\n");
    }
}