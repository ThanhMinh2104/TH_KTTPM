package com.example.dbpartition.repository;

import com.example.dbpartition.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByGender(String gender);

    List<User> findByNameContainingIgnoreCase(String name);
}
