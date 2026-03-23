package com.example.dbpartition.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * HORIZONTAL PARTITION entity
 *
 * Cung mot entity nay duoc luu vao:
 *   - DB_MALE   (neu gender = "MALE" / "NAM")
 *   - DB_FEMALE (neu gender = "FEMALE" / "NU")
 *
 * Spring Boot tu dong chon dung DataSource
 * thong qua UserRoutingDataSource.
 */
@Entity
@Table(name = "table_user")   // ten table giong nhau o ca 2 DB
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    /**
     * "MALE" / "NAM"   --> luu vao table_user_01 (DB_MALE)
     * "FEMALE" / "NU"  --> luu vao table_user_02 (DB_FEMALE)
     */
    @Column(nullable = false)
    private String gender;

    private Integer age;
}
