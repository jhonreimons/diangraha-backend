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
    @Schema(description = "Unique identifier for user", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Unique username for user login", example = "john_doe", required = true, maxLength = 50)
    private String username;

    @Column(nullable = false)
    @Schema(description = "User password (hashed)", example = "$2a$10$...", required = true, minLength = 8, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

}
