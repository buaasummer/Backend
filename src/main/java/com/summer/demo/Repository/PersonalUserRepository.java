package com.summer.demo.Repository;
import com.summer.demo.Entity.PersonalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonalUserRepository extends JpaRepository<PersonalUser,Integer> {
    @Query("select COUNT(u) from PersonalUser u where u.email=?1")
    public int getNumberOfemail(String email);

    public PersonalUser findByemail(String email);

    public PersonalUser findByusername(String username);

    public PersonalUser findByUserId(int userId);
}
