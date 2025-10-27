package com.voting_system.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "vote_records", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"voter_id", "election_id"})
})

public class VoteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Voter voter;

    @ManyToOne(optional = false)
    private Election election;

    public VoteRecord() {}

    public VoteRecord(Voter voter, Election election) {
        this.voter = voter;
        this.election = election;
    }

    public Long getId() {
        return id;
    }

    public Voter getVoter() {
        return voter;
    }

    public Election getElection() {
        return election;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    public void setElection(Election election) {
        this.election = election;
    }

}
