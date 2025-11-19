package com.voting_system.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "vote_audit")
public class VoteAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "election_name", nullable = false)
    private String electionName;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    public VoteAudit() {}

    public VoteAudit(String electionName, Instant timestamp) {
        this.electionName = electionName;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getElectionName() { return electionName; }
    public void setElectionName(String electionName) { this.electionName = electionName; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

}