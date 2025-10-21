package com.diangraha_backend.diangraha_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sub_service_works")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubServiceWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_service_id")
    @JsonManagedReference
    private SubService subService;
}
