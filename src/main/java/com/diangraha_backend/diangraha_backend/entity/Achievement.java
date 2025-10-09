package com.diangraha_backend.diangraha_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String imageUrl;

}
