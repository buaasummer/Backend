package com.summer.demo.Repository;

import com.summer.demo.Entity.PersonalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<PersonalUser,Integer> {
    public PersonalUser findByUserId(int userId);
}
