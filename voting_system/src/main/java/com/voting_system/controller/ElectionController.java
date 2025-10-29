package com.voting_system.controller;

import com.voting_system.entity.Election;
import com.voting_system.entity.Voter;
import com.voting_system.service.ElectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/elections/{name}/vote")
    public ResponseEntity<?> vote(
            @PathVariable String name,
            @RequestBody Map<String, String> payload,
            HttpSession session) {

        Voter voter = (Voter) session.getAttribute("voter");

        if (voter == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("No voter logged in. Please log in first.");
        }

        String candidate = payload.get("candidate");

        try {
            electionService.vote(name, candidate, voter.getUsername());
            return ResponseEntity.ok("Vote recorded successfully for " + candidate);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error recording vote.");
        }
    }
}
