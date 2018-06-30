package com.summer.demo.Repository;
import com.summer.demo.Entity.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import com.summer.demo.Entity.Institution;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    public Page<Meeting> findMeetingsByTitleContaining(String title, Pageable pageable);
    public List<Meeting> findAllByInstitution(Institution institution);
}
