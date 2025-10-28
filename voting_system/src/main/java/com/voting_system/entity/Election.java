package com.voting_system.entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "elections")
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "election_name", nullable = false)
    private String electionName;

    @ElementCollection
    @CollectionTable(name = "election_candidates", joinColumns = @JoinColumn(name = "election_id"))
    @Column(name = "candidates")
    private Set<String> candidates = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "election_results", joinColumns = @JoinColumn(name = "election_id"))
    @MapKeyColumn(name = "candidate_name")
    @Column(name = "vote_count")
    private Map<String, Integer> results = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "election_voters", joinColumns = @JoinColumn(name = "election_id"))
    @Column(name = "voter_username")
    private Set<String> voters = new HashSet<>();

    public Election() {}

    public Election(String electionName) {
        this.electionName = electionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public Set<String> getCandidates() {
        return candidates;
    }

    public void setCandidates(Set<String> candidates) {
        this.candidates = candidates;
    }

    public Map<String, Integer> getResults() {
        return results;
    }

    public void setResults(Map<String, Integer> results) {
        this.results = results;
    }

    public Set<String> getVoters() {
        return voters;
    }

    public void setVoters(Set<String> voters) {
        this.voters = voters;
    }
}
