package com.voting_system.controller;

import com.voting_system.entity.Voter;
import com.voting_system.entity.Election;
import com.voting_system.dto.VoteRequest;
import com.voting_system.service.VoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.voting_system.dto.VoteRequest;



@Controller
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<String> castVote(@RequestBody VoteRequest voteRequest, HttpSession session) {
        Voter voter = (Voter) session.getAttribute("user");

        if (voter == null || voter.getRole() != Voter.Role.VOTER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only voters can vote.");
        }

        voteService.castVote(voter, voteRequest.getElectionId(), voteRequest.getCandidate());
        return ResponseEntity.ok("Vote cast successfully");
    }

}
