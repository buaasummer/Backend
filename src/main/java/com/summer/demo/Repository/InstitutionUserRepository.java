package com.summer.demo.Repository;
import com.summer.demo.Entity.PersonalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstitutionUserRepository extends JpaRepository<PersonalUser,Integer> {
    @Query("select COUNT(u) from InstitutionUser u where u.username=?1")
    public int getNumberOfusername(String username);
}
