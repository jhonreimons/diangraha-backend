package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.SubServiceWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubServiceWorkRepository extends JpaRepository<SubServiceWork, Long> {
    List<SubServiceWork> findBySubService_Id(Long subServiceId);
}