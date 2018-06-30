package com.summer.demo.Repository;

import com.summer.demo.Entity.Meeting;
import com.summer.demo.Entity.Paper;
import com.summer.demo.Entity.PersonalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperRepository extends JpaRepository<Paper,Integer> {
    public List<Paper> findPapersByPersonalUserAndMeeting(PersonalUser personalUser,Meeting meeting);

}
