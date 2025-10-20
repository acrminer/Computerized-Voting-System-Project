package com.voting_system.controller;

import com.voting_system.entity.Voter;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/voter/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        Voter voter = (Voter) session.getAttribute("voter");
        model.addAttribute("voter", voter);
        return "dashboard";
    }
}
