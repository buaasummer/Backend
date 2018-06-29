package com.summer.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonalUserRepository extends JpaRepository<Institution,Integer> {
    @Query("select COUNT(u) from PersonalUser u where u.email=?1")
    public int getNumberOfemail(String email);
}

