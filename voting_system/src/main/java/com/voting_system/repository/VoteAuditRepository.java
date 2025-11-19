package com.voting_system.repository;

import com.voting_system.entity.VoteAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteAuditRepository extends JpaRepository<VoteAudit, Long> {
    List<VoteAudit> findByElectionNameOrderByTimestampDesc(String electionName);
}
