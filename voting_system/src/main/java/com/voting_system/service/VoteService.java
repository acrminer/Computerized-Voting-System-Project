package com.voting_system.service;

import com.voting_system.entity.Vote;
import com.voting_system.entity.Voter;
import com.voting_system.entity.Election;
import com.voting_system.repository.VoteRepository;
import com.voting_system.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private ElectionRepository electionRepository;

    public void castVote(Voter voter, Long electionId, String candidate) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new IllegalArgumentException("Election not found"));

        if (!election.getCandidates().contains(candidate)) {
            throw new IllegalArgumentException("Invalid candidate: " + candidate);
        }

        boolean alreadyVoted = voteRepository.existsByVoterAndElection(voter, election);
        if (alreadyVoted) {
            throw new IllegalStateException("You have already voted in this election.");
        }

        election.getResults().merge(candidate, 1, Integer::sum);
        electionRepository.save(election);

        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setElection(election);
        vote.setCandidate(candidate);
        voteRepository.save(vote);
    }

}
