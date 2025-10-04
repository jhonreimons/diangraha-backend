package com.diangraha_backend.diangraha_backend.repository;


import com.diangraha_backend.diangraha_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}