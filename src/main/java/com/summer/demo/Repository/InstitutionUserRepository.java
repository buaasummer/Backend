package com.summer.demo.Repository;
import com.summer.demo.Entity.InstitutionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstitutionUserRepository extends JpaRepository<InstitutionUser,Integer> {
    @Query("select COUNT(u) from InstitutionUser u where u.username=?1")
    public int getNumberOfUsername(String username);
}
