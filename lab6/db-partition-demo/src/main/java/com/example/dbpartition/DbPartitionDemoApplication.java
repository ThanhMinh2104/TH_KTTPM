package com.example.dbpartition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// Tat auto-config DataSource vi minh tu cau hinh routing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DbPartitionDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbPartitionDemoApplication.class, args);
    }
}
