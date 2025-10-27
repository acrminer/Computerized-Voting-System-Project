package com.voting_system.repository;

import com.voting_system.entity.Voter;
import com.voting_system.entity.Vote;
import com.voting_system.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByVoterAndElection(Voter voter, Election election);
}
