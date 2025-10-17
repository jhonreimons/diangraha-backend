package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubServiceRepository extends JpaRepository<SubService, Long> {
    List<SubService> findByServiceId(Long serviceId);
}