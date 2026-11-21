package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.ApplicationRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(User user) {

        User existingUser = repo.findByEmail(user.getEmail());

        if (existingUser != null) {
            return "register";
        }

        repo.save(user);

        System.out.println("User Registered Successfully");

        return "login";
    }

 
}