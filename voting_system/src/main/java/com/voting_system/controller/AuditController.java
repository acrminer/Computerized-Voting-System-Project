package com.voting_system.controller;

import com.voting_system.entity.User;
import com.voting_system.entity.VoteAudit;
import com.voting_system.service.AuditService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuditController {

    private final AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/audit")
    public String showAuditTrail(@RequestParam(required = false) String electionName,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        if (!"admin".equalsIgnoreCase(user.getRole())) {
            return "redirect:/user/dashboard";
        }

        List<VoteAudit> audits = (electionName != null && !electionName.isBlank())
                ? auditService.getAuditTrail(electionName)
                : List.of();

        model.addAttribute("audits", audits);
        model.addAttribute("electionName", electionName);
        model.addAttribute("user", user); // so audit.html can still show username
        return "audit";
    }

}