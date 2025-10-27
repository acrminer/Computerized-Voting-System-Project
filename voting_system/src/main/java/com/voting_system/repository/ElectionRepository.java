package com.voting_system.repository;

import com.voting_system.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> { //Changed Integer to long

    Election findByElectionName(String electionName);
}
