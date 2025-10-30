package com.voting_system.service;
import com.voting_system.entity.User;
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

    public List<User> getAllUsers() {
        return voterRepository.findAll();
    }

    public boolean userExists(String username) {
        return voterRepository.findByUsername(username) != null;
    }

    public User addUser(User user) {
        User existingUser = voterRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        return voterRepository.save(user);
    }

    public void deleteUser(User user) {
        voterRepository.deleteById(user.getId());
    }

    public User loginUser(String username, String password) {
        User user = voterRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
