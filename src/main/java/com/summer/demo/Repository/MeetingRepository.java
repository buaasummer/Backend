package com.summer.demo.Repository;
import com.summer.demo.Entity.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    public Page<Meeting> findMeetingsByTitleContaining(String title, Pageable pageable);
}
