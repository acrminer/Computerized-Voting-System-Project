package com.voting_system.repository;

import com.voting_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<User, Long> {

    // Method to find user by username
    User findByUsername(String username);
}
