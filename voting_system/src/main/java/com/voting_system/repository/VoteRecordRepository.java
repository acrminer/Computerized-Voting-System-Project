package com.voting_system.repository;

import com.voting_system.entity.VoteRecord;
import com.voting_system.entity.Voter;
import com.voting_system.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {
    boolean existsByVoterAndElection(Voter voter, Election election);
}

