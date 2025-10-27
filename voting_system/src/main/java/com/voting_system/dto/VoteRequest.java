package com.voting_system.dto;

public class VoteRequest {
    private Long electionId;
    private String candidate;

    // Getters and setters
    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

}
