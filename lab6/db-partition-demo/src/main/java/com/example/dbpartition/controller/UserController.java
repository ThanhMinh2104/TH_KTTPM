package com.example.dbpartition.controller;

import com.example.dbpartition.entity.User;
import com.example.dbpartition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API cho Horizontal Partition Demo
 *
 * Test bang Postman hoac curl:
 *
 *   POST /api/users
 *   Body: {"name":"An","email":"an@x.com","gender":"MALE","age":25}
 *
 *   GET  /api/users?gender=MALE
 *   GET  /api/users/all
 *   GET  /api/users/{id}?gender=MALE
 *   DELETE /api/users/{id}?gender=FEMALE
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Tao user moi — tu dong luu vao dung partition
     *
     * POST /api/users
     * {
     *   "name": "Nguyen Van An",
     *   "email": "an@example.com",
     *   "gender": "MALE",
     *   "age": 25
     * }
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saved = userService.saveUser(user);
        return ResponseEntity.ok(saved);
    }

    /**
     * Lay users theo gender (query dung 1 partition)
     * GET /api/users?gender=MALE
     * GET /api/users?gender=FEMALE
     */
    @GetMapping
    public ResponseEntity<List<User>> getUsersByGender(
            @RequestParam(defaultValue = "MALE") String gender) {
        return ResponseEntity.ok(userService.getUsersByGender(gender));
    }

    /**
     * Lay tat ca users tu ca 2 partition (merge)
     * GET /api/users/all
     */
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Lay user theo ID (can truyen gender de biet partition nao)
     * GET /api/users/1?gender=MALE
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @PathVariable Long id,
            @RequestParam String gender) {
        return ResponseEntity.ok(userService.getUserById(id, gender));
    }

    /**
     * Xoa user
     * DELETE /api/users/1?gender=FEMALE
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestParam String gender) {
        userService.deleteUser(id, gender);
        return ResponseEntity.noContent().build();
    }
}
