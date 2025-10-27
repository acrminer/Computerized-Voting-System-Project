package com.voting_system.service;
import com.voting_system.entity.Voter;
import com.voting_system.repository.VoterRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import  org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public List<Voter> getAllUsers() {
        return voterRepository.findAll();
    }

    public boolean userExists(String username) {
        return voterRepository.findByUsername(username) != null;
    }

    public Voter addUser(Voter voter) {
        Voter existingVoter = voterRepository.findByUsername(voter.getUsername());
        if (existingVoter != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        voter.setRole(Voter.Role.VOTER);
        voter.setPassword(passwordEncoder.encode(voter.getPassword()));
        return voterRepository.save(voter);
    }

    public void deleteUser(Voter voter) {
        voterRepository.deleteById(voter.getId());
    }

    public boolean loginUser(String username, String password) {
        Voter voter = voterRepository.findByUsername(username);
        return voter != null && passwordEncoder.matches(password, voter.getPassword());
    }
}
