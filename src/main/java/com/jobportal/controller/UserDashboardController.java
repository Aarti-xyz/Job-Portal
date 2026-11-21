package com.jobportal.controller;

import java.io.File;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import com.jobportal.entity.User;

import com.jobportal.repository.UserRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.ApplicationRepository;

@Controller
public class UserDashboardController {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("/resume")
    public String resume() {
        return "user/resume";
    }

    @PostMapping("/resume")
    public String uploadResume(@RequestParam("resume") MultipartFile file) {

        try {

            String uploadDir = "C:/JobPortalResumes/";

            File folder = new File(uploadDir);

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File destination = new File(folder, file.getOriginalFilename());

            file.transferTo(destination);

            System.out.println("Resume Uploaded Successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/resume";
    }
    
    @GetMapping("/edit-profile")
    public String editProfile(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "user/edit-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(User updatedUser, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setMobile(updatedUser.getMobile());
        user.setAddress(updatedUser.getAddress());

        userRepo.save(user);

        session.setAttribute("user", user);

        return "redirect:/profile";
    }
    @GetMapping("/user/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("user");
        
        System.out.println("Session User = " + user);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("jobCount", jobRepo.count());
        model.addAttribute("applicationCount", applicationRepo.count());

        
        model.addAttribute("resumeStatus", "Uploaded");
        model.addAttribute("profileStatus", "Completed");

        return "user/dashboard";
    }
    
}
