package com.summer.demo.Repository;

import com.summer.demo.Entity.Meeting;
import com.summer.demo.Entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants,Integer> {
    @Query(value="select * from participants where meeting_id=?1 and paper_number<>0",nativeQuery = true)
    public List<Participants> findAuthorParticipants(int meetingId);
    @Query(value="select * from participants where meeting_id=?1 and paper_number=0",nativeQuery = true)
    public List<Participants> findAuditParticipants(int meetingId);
}
