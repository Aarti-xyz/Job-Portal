package com.jobportal.controller;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import com.jobportal.entity.Application;
import com.jobportal.repository.ApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jobportal.entity.User;
import com.jobportal.entity.Job;
import com.jobportal.repository.JobRepository;

@Controller
public class JobController {

    @Autowired
    private JobRepository repo;
    
    
    
   

    @GetMapping("/jobs")
    public String viewJobs(Model model) {

        List<Job> jobs = repo.findAll();

        model.addAttribute("jobs", jobs);

        return "user/jobs";
    }

    @GetMapping("/search")
    public String searchJobs(@RequestParam String keyword, Model model) {

        List<Job> jobs = repo.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(keyword, keyword);

        if (jobs.isEmpty()) {
            jobs = repo.findByLocationContainingIgnoreCase(keyword);
        }

        model.addAttribute("jobs", jobs);

        return "user/jobs";
    }
   
}


