package com.voting_system.controller;

import com.voting_system.entity.User;
import com.voting_system.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "success";
    }

    @PostMapping("/loginUser")
    @ResponseBody
    public String login(@RequestBody User user, HttpSession session) {
       User loginUser = userService.loginUser(user.getUsername(), user.getPassword());
       if (loginUser != null) {
           session.setAttribute("user", loginUser);
           return "Login successful!";
       }
       return "Login failed!";

    }
}
