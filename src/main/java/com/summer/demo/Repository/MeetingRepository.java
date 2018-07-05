package com.summer.demo.Repository;
import com.summer.demo.Entity.Meeting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.sql.Date;
import org.springframework.data.domain.Page;
import com.summer.demo.Entity.Institution;
import org.springframework.data.jpa.repository.Query;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    public Page<Meeting> findMeetingsByTitleContaining(String title, Pageable pageable);

    @Query(value = "select * from meeting m where m.title like ?1 " +
            "and (m.start_date between ?2 and ?3) " +
            "and (m.end_date between ?2 and ?3) " +
            "and ?4 between m.post_start_date and m.post_end_date", nativeQuery = true)
    public Page<Meeting> findAllByGivenPost(String title, Date startdate, Date enddate, Date curdate, Pageable pageable);

    @Query(value = "select * from meeting m where m.title like ?1 " +
            "and (m.start_date between ?2 and ?3) " +
            "and (m.end_date between ?2 and ?3) " +
            "and ?4 between m.regist_start_date and m.registration_deadline", nativeQuery = true)
    public Page<Meeting> findAllByGivenRegist(String title, Date startdate, Date enddate, Date curdate, Pageable pageable);

    @Query(value = "select * from meeting m where m.title like ?1 " +
            "and (m.start_date between ?2 and ?3) " +
            "and (m.end_date between ?2 and ?3) ", nativeQuery = true)
    public Page<Meeting> findAllByGivenNone(String title, Date startdate, Date enddate, Pageable pageable);

    public Page<Meeting> findAllByInstitution(Institution institution, Pageable pageable);

    public Meeting findByMeetingId(int meetingId);
}
