package com.voting_system.service;

import com.voting_system.entity.VoteAudit;
import com.voting_system.repository.VoteAuditRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class AuditService {

    private final VoteAuditRepository voteAuditRepository;

    @PostConstruct
    public void init() {
        System.out.println("AuditService initialized");
    }

    @Autowired
    public AuditService(VoteAuditRepository voteAuditRepository) {
        this.voteAuditRepository = voteAuditRepository;
    }

    public void logVoteAudit(String electionName) {
        VoteAudit audit = new VoteAudit(electionName, Instant.now());
        voteAuditRepository.save(audit);
    }

    // Add methods here to retrieve audit data for the audit.html page later
    public List<VoteAudit> getAuditTrail(String electionName) {
        return voteAuditRepository.findByElectionNameOrderByTimestampDesc(electionName);
    }
}