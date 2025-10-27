package com.voting_system.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Removed to keep anonymity
    /* @ManyToOne(optional = false) // This means each vote is linked to one voter, and that voter must be present
    private Voter voter; */

    @ManyToOne(optional = false) // Each vote is also linked to one election, and again, it’s required
    private Election election;

    @Column(nullable = false)
    private String candidate;

    // Optional: timestamp, status, etc.
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, unique = true)
    private String voteId = UUID.randomUUID().toString();

    // public void setVoter(Voter voter) { this.voter = voter;}

    public void setElection(Election election) { this.election = election;}

    public void setCandidate(String candidate) { this.candidate = candidate;}

    public void setTimestamp(LocalDateTime now) {  this.timestamp = now;}
}