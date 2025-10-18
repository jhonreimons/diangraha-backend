package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    @Query("SELECT DISTINCT s FROM ServiceEntity s LEFT JOIN FETCH s.features")
    List<ServiceEntity> findAllWithFeatures();
    
    @Query("SELECT s FROM ServiceEntity s LEFT JOIN FETCH s.features WHERE s.id = :id")
    java.util.Optional<ServiceEntity> findByIdWithFeatures(Long id);
}
