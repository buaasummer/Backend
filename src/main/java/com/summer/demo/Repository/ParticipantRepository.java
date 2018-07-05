package com.summer.demo.Repository;

import com.summer.demo.Entity.Paper;
import com.summer.demo.Entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {

}
