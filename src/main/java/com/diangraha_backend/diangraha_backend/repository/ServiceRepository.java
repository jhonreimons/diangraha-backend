package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    @Query("SELECT DISTINCT s FROM ServiceEntity s " +
            "LEFT JOIN FETCH s.features f " +
            "LEFT JOIN FETCH s.subServices sub " +
            "LEFT JOIN FETCH sub.works w")
    List<ServiceEntity> findAllWithFeatures();

    @Query("SELECT DISTINCT s FROM ServiceEntity s " +
            "LEFT JOIN FETCH s.features f " +
            "LEFT JOIN FETCH s.subServices sub " +
            "LEFT JOIN FETCH sub.works w " +
            "WHERE s.id = :id")
    Optional<ServiceEntity> findByIdWithFeatures(@Param("id") Long id);
}
