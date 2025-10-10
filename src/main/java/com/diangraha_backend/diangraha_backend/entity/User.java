package com.diangraha_backend.diangraha_backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Getter
@Setter
@Table(name = "users")
@Schema(description = "User entity representing users table in database")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for user", example = "1")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @Schema(description = "Unique username for user login", example = "john_doe", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50)
    private String username;

    @Column(nullable = false)
    @Schema(description = "User password (hashed)", example = "$2a$10$...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
