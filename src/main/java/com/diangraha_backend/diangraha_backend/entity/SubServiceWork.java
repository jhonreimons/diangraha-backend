package com.diangraha_backend.diangraha_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sub_service_works")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "subService")
public class SubServiceWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_service_id", nullable = false)
    @JsonBackReference(value = "subservice-work")
    private SubService subService;
}
