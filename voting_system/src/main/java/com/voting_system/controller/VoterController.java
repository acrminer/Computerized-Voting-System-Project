package com.voting_system.controller;

import com.voting_system.entity.Voter;
import com.voting_system.service.VoterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoterController {

    @Autowired
    private VoterService voterService;

    @PostMapping("/addVoter")
    public String addUser(@RequestBody Voter voter) {
        voterService.addUser(voter);
        return "success";
    }

    @PostMapping("/loginVoter")
    @ResponseBody
    public String login(@RequestBody Voter voter, HttpSession session) {
       boolean success = voterService.loginUser(voter.getUsername(), voter.getPassword());
       session.setAttribute("voter", voter);
       return success ? "Login successful!" : "Login failed";
    }
}
