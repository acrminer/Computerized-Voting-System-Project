package com.voting_system.controller;

import com.voting_system.entity.Election;
import com.voting_system.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping("/addElection")
    public String addElection(@RequestBody Election election) {
        electionService.addElection(election);
        return "success";
    }

    @PostMapping("updateElection")
    public String updateElection(@RequestBody Election election) {
        electionService.updateElection(election);
        return "success";
    }

    @PostMapping("/elections")
    public ResponseEntity<Election> createElection(@RequestBody Election election) {
        Election savedElection = electionService.addElection(election);
        return ResponseEntity.ok(savedElection);
    }

    @GetMapping("/elections")
    public List<Election> getAllElections() {
        return electionService.getAll();
    }
}
