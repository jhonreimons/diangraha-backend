package com.diangraha_backend.diangraha_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String featureName;

    private String featureDesc;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    @JsonManagedReference
    private ServiceEntity service;
}
