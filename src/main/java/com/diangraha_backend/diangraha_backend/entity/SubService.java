package com.diangraha_backend.diangraha_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sub_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"service", "works"})
public class SubService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    @JsonBackReference(value = "service-subservice")
    private ServiceEntity service;

    @OneToMany(mappedBy = "subService", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "subservice-work")
    @Builder.Default
    private List<SubServiceWork> works = new ArrayList<>();
}
