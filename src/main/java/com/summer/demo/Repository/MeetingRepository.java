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
            "and (m.startdate between ?2 and ?3) " +
            "and (m.enddate between ?2 and ?3) " +
            "and ?4 between m.poststartdate and m.postenddate", nativeQuery = true)
    public Page<Meeting> findAllByGivenPost(String title, Date startdate, Date enddate, Date curdate, Pageable pageable);

    @Query(value = "select * from meeting m where m.title like ?1 " +
            "and (m.startdate between ?2 and ?3) " +
            "and (m.enddate between ?2 and ?3) " +
            "and ?4 between m.registstartdate and m.registrationdeadline", nativeQuery = true)
    public Page<Meeting> findAllByGivenRegist(String title, Date startdate, Date enddate, Date curdate, Pageable pageable);

    @Query(value = "select * from meeting m where m.title like ?1 " +
            "and (m.startdate between ?2 and ?3) " +
            "and (m.enddate between ?2 and ?3) ", nativeQuery = true)
    public Page<Meeting> findAllByGivenNone(String title, Date startdate, Date enddate, Pageable pageable);
    public List<Meeting> findAllByInstitution(Institution institution);
    public Meeting findByMeetingId(int meetingId);
}
