package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.SubServiceWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServiceWorkRepository extends JpaRepository <SubServiceWork, Long> {
}
