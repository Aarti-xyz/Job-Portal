package com.jobportal.controller;
import com.jobportal.entity.Application;
import jakarta.servlet.http.HttpSession;
import com.jobportal.entity.Job;

import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ApplicationController{
	@Autowired
	private ApplicationRepository applicationRepo;
	@Autowired
	private JobRepository jobRepo;
	@GetMapping("/apply-job")
	public String applyJob(@RequestParam int id, HttpSession session) {

	    User user=(User) session.getAttribute("user");
	    
	    System.out.println("Logged in User: " + user);

	    if (user==null) {
	        return "redirect:/login";
	    }

	    Job job=jobRepo.findById(id).orElse(null);
	    
	    System.out.println("Selected Job: " + job);

	    if (job!=null) {

	        if (!applicationRepo.existsByUserEmailAndJobTitle(user.getEmail(), job.getTitle())) {

	            Application app = new Application();

	            app.setUserEmail(user.getEmail());
	            app.setJobTitle(job.getTitle());
	            app.setCompany(job.getCompany());
	            app.setStatus("Pending");

	            applicationRepo.save(app);
	            System.out.println("Application Saved Successfully");
	        } else {
	            System.out.println("Already Applied");
	        }
	    }

	    return "redirect:/jobs";
	}

	@GetMapping("/applications")
	public String viewApplications(HttpSession session, Model model) {

	    User user = (User) session.getAttribute("user");

	    if (user == null) {
	        return "redirect:/login";
	    }

	    System.out.println("Session Email = [" + user.getEmail() + "]");

	    var applications = applicationRepo.findByUserEmail(user.getEmail());

	    System.out.println("Applications Found = " + applications.size());

	    model.addAttribute("applications", applications);

	    return "user/applications";
	}
    
    
    @GetMapping("/accept")
    public String acceptApplication(@RequestParam int id) {

        Application app = applicationRepo.findById(id).orElse(null);

        if (app != null) {
            app.setStatus("Accepted");
            applicationRepo.save(app);
        }

        return "redirect:/recruiter/applications";
    }
    
    @GetMapping("/reject")
    public String rejectApplication(@RequestParam int id) {

        Application app = applicationRepo.findById(id).orElse(null);

        if (app != null) {
            app.setStatus("Rejected");
            applicationRepo.save(app);
        }

        return "redirect:/recruiter/applications";
    }


    }
   	