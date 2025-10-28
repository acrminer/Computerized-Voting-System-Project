package com.voting_system.service;

import com.voting_system.entity.Election;
import com.voting_system.repository.ElectionRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElectionService {

    private final ElectionRepository repo;
    private final ElectionRepository electionRepository;

    public ElectionService(ElectionRepository repo, ElectionRepository electionRepository) {
        this.repo = repo;
        this.electionRepository = electionRepository;
    }

    public List<Election> getAll() {
        return repo.findAll();
    }

    public boolean electionExists(String electionName) {
        return electionRepository.findByElectionName(electionName) != null;
    }

    public Election addElection(Election election){
        Election existingElection = electionRepository.findByElectionName(election.getElectionName());
        if (existingElection != null) {
            throw new IllegalArgumentException("Election already exists");
        }
        else{
            Map<String, Integer> initialResults = new HashMap<>();
            for (String candidate : election.getCandidates()) {
                initialResults.put(candidate, 0);
            }
            election.setResults(initialResults);
            return electionRepository.save(election);
        }
    }

    public Election updateElection(Election updatedElection) {
        Election existingElection = electionRepository.findByElectionName(updatedElection.getElectionName());
        if (existingElection != null) {
            existingElection.setElectionName(updatedElection.getElectionName());
            existingElection.setResults(updatedElection.getResults());
        }
        return electionRepository.save(existingElection);
    }

    public void vote(String electionName, String candidate, String username) {
        Election election = electionRepository.findByElectionName(electionName);
        if (election == null) {
            throw new IllegalArgumentException("Election not found");
        }
        if(!election.getCandidates().contains(candidate)) {
            throw new IllegalArgumentException("Candidate not found");
        }

        Map<String, Integer> results = election.getResults();
        if (results == null) {
            results = new HashMap<>();
        }

        // Check if user already voted
        if (election.getVoters().contains(username)) {
            throw new IllegalArgumentException("You have already voted");
        }

        results.put(candidate, results.getOrDefault(candidate, 0) + 1);
        election.setResults(results);
        electionRepository.save(election);
    }
}
