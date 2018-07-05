package com.summer.demo.Repository;

import com.summer.demo.Entity.Participants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants,Integer> {

}
