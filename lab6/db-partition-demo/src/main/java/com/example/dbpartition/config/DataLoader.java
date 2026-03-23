package com.example.dbpartition.config;

import com.example.dbpartition.entity.User;
import com.example.dbpartition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Tu dong tao du lieu mau khi chay app
 * Xem ket qua tai:
 *   GET http://localhost:8080/api/users/all
 *   GET http://localhost:8080/api/users?gender=MALE
 *   GET http://localhost:8080/api/users?gender=FEMALE
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) {
        System.out.println("\n========== LOADING DEMO DATA ==========");

        // Users Nam -> luu vao DB_MALE (table_user_01)
        userService.saveUser(new User(null, "Nguyen Van An",  "an@mail.com",    "MALE",   25));
        userService.saveUser(new User(null, "Tran Van Binh",  "binh@mail.com",  "MALE",   30));
        userService.saveUser(new User(null, "Le Van Cuong",   "cuong@mail.com", "MALE",   22));

        // Users Nu -> luu vao DB_FEMALE (table_user_02)
        userService.saveUser(new User(null, "Nguyen Thi Lan", "lan@mail.com",   "FEMALE", 24));
        userService.saveUser(new User(null, "Tran Thi Mai",   "mai@mail.com",   "FEMALE", 28));
        userService.saveUser(new User(null, "Le Thi Huong",   "huong@mail.com", "FEMALE", 26));

        System.out.println("========== DATA LOADED ==========\n");
        System.out.println("Test API:");
        System.out.println("  GET  http://localhost:8080/api/users/all");
        System.out.println("  GET  http://localhost:8080/api/users?gender=MALE");
        System.out.println("  GET  http://localhost:8080/api/users?gender=FEMALE");
        System.out.println("  POST http://localhost:8080/api/users");
        System.out.println("  H2 Console: http://localhost:8080/h2-console\n");
    }
}
