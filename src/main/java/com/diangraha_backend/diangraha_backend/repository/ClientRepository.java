package com.diangraha_backend.diangraha_backend.repository;

import com.diangraha_backend.diangraha_backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ClientRepository extends JpaRepository <Client, Long>
{

}
