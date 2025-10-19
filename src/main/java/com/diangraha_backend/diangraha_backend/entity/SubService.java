package com.diangraha_backend.diangraha_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sub_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

    @OneToMany(mappedBy = "subService", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SubServiceWork> works = new ArrayList<>();
}
