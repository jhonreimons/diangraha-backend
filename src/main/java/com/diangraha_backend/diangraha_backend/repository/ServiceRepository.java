package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.ServiceEntity;
import com.diangraha_backend.diangraha_backend.entity.SubService;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    // Ambil semua service + features
    @Query("SELECT DISTINCT s FROM ServiceEntity s LEFT JOIN FETCH s.features")
    List<ServiceEntity> findAllWithFeatures();

    // Ambil semua service + subServices
    @Query("SELECT DISTINCT s FROM ServiceEntity s LEFT JOIN FETCH s.subServices")
    List<ServiceEntity> findAllWithSubServices();

    // Ambil semua subService + works
    @Query("SELECT DISTINCT ss FROM SubService ss LEFT JOIN FETCH ss.works w")
    List<SubService> findAllSubServicesWithWorks();

    @EntityGraph(attributePaths = {"features", "subServices"})
    @Query("SELECT s FROM ServiceEntity s WHERE s.id = :id")
    Optional<ServiceEntity> findByIdWithDetails(Long id);
}
