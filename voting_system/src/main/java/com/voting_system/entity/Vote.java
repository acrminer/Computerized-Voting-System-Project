package com.voting_system.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Voter voter;

    @ManyToOne(optional = false)
    private Election election;

    @Column(nullable = false)
    private String candidate;

    public void setVoter(Voter voter) { this.voter = voter;}

    public void setElection(Election election) { this.election = election;}

    public void setCandidate(String candidate) { this.candidate = candidate;}

    // Optional: timestamp, status, etc.
}