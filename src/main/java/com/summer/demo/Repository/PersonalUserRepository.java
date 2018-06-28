package com.summer.demo.Repository;
import com.summer.demo.Entity.PersonalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonalUserRepository extends JpaRepository<PersonalUser,Integer> {
    @Query("select COUNT(u) from PersonalUser u where u.username=?1")
    public int getNumberOfusername(String username);

    public PersonalUser findByusername(String username);

    public PersonalUser findByUserId(int userId);
}
