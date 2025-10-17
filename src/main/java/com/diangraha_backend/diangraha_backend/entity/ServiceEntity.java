package com.diangraha_backend.diangraha_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"features", "subServices"})
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "short_desc")
    private String shortDesc;

    @Column(name = "long_desc")
    private String longDesc;

    @Column(name = "image_url")
    private String imageUrl;

    // === Relasi ke ServiceFeature ===
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "service-feature")
    @Builder.Default
    private List<ServiceFeature> features = new ArrayList<>();

    // === Relasi ke SubService ===
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "service-subservice")
    @Builder.Default
    private List<SubService> subServices = new ArrayList<>();
}
