package com.voting_system.repository;

import com.voting_system.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<Voter, Long> {

    // Method to find user by username
    Voter findByUsername(String username);
}
