package com.voting_system.service;
import com.voting_system.entity.User;
import com.voting_system.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import  org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public User addUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
