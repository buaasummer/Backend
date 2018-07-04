package com.summer.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.summer.demo.Entity.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    public Administrator findByUsername(String username);
}
