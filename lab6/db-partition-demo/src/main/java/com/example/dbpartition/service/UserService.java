package com.example.dbpartition.service;

import com.example.dbpartition.config.UserRoutingDataSource;
import com.example.dbpartition.entity.User;
import com.example.dbpartition.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HORIZONTAL PARTITION — Service Layer
 *
 * Day la noi chua logic phan vung chinh:
 *   1. Kiem tra gender cua User
 *   2. Set dung DataSource truoc khi goi DB
 *   3. Thuc hien operation
 *   4. Clear ThreadLocal sau khi xong
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // =========================================================
    //  SAVE — tu dong chon partition theo gender
    // =========================================================

    /**
     * Luu user vao dung partition:
     *   MALE   --> DB_MALE   (table_user_01)
     *   FEMALE --> DB_FEMALE (table_user_02)
     */
    public User saveUser(User user) {
        // *** CORE LOGIC: chon partition dua tren condition ***
        UserRoutingDataSource.setByGender(user.getGender());

        try {
            User saved = userRepository.save(user);
            System.out.printf("[Service] Saved user '%s' (gender=%s) to %s partition%n",
                    user.getName(), user.getGender(),
                    user.getGender().toUpperCase());
            return saved;
        } finally {
            UserRoutingDataSource.clear(); // QUAN TRONG: luon clear
        }
    }

    // =========================================================
    //  GET ALL — lay tat ca tu 1 partition
    // =========================================================

    public List<User> getAllMaleUsers() {
        UserRoutingDataSource.useMale(); // --> DB_MALE
        try {
            return userRepository.findAll();
        } finally {
            UserRoutingDataSource.clear();
        }
    }

    public List<User> getAllFemaleUsers() {
        UserRoutingDataSource.useFemale(); // --> DB_FEMALE
        try {
            return userRepository.findAll();
        } finally {
            UserRoutingDataSource.clear();
        }
    }

    public List<User> getUsersByGender(String gender) {
        UserRoutingDataSource.setByGender(gender);
        try {
            return userRepository.findAll();
        } finally {
            UserRoutingDataSource.clear();
        }
    }

    // =========================================================
    //  GET BY ID — can biet gender de chon partition
    // =========================================================

    public User getUserById(Long id, String gender) {
        UserRoutingDataSource.setByGender(gender);
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found: " + id));
        } finally {
            UserRoutingDataSource.clear();
        }
    }

    // =========================================================
    //  DELETE
    // =========================================================

    public void deleteUser(Long id, String gender) {
        UserRoutingDataSource.setByGender(gender);
        try {
            userRepository.deleteById(id);
        } finally {
            UserRoutingDataSource.clear();
        }
    }

    // =========================================================
    //  LAY TU CA 2 PARTITION (merge)
    // =========================================================

    public List<User> getAllUsers() {
        // Lay tu Male partition
        UserRoutingDataSource.useMale();
        List<User> maleUsers;
        try {
            maleUsers = userRepository.findAll();
        } finally {
            UserRoutingDataSource.clear();
        }

        // Lay tu Female partition
        UserRoutingDataSource.useFemale();
        List<User> femaleUsers;
        try {
            femaleUsers = userRepository.findAll();
        } finally {
            UserRoutingDataSource.clear();
        }

        // Merge 2 list
        maleUsers.addAll(femaleUsers);
        return maleUsers;
    }
}
