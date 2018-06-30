package com.summer.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.summer.demo.Entity.Institution;

public interface InstitutionRepository extends JpaRepository<Institution,Integer> {
    @Query("select COUNT(u) from Institution u where u.institutionName=?1")
    public int getNumberOfInstitutionName(String institutionName);

    public Institution findByInstitutionId(int institutionId);
    public Institution findByInstitutionName(String institutionName);
}