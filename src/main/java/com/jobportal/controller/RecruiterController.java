package com.jobportal.controller;



import org.springframework.beans.factory.annotation.Autowired;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jobportal.entity.Job;
import com.jobportal.repository.JobRepository;
import com.jobportal.entity.Application;  


@Controller
public class RecruiterController {

    @Autowired
    private JobRepository jobRepo;
    
    @Autowired
    private ApplicationRepository applicationRepo;

  
    @GetMapping("/recruiter/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalJobs", jobRepo.count());

        model.addAttribute("totalApplications", applicationRepo.count());

        model.addAttribute("pending",
                applicationRepo.countByStatus("Pending"));

        model.addAttribute("accepted",
                applicationRepo.countByStatus("Accepted"));

        model.addAttribute("rejected",
                applicationRepo.countByStatus("Rejected"));

        model.addAttribute("jobs", jobRepo.findAll());

        return "recruiter/dashboard";
    }
   
    @GetMapping("/add-job")
    public String addJobPage(Model model) {

        model.addAttribute("job", new Job());

        return "recruiter/add-job";
    }

    
    @PostMapping("/save-job")
    public String saveJob(@ModelAttribute Job job) {

        jobRepo.save(job);

        return "redirect:/recruiter/dashboard";
    }

    
    @GetMapping("/delete-job/{id}")
    public String deleteJob(@PathVariable int id) {

        jobRepo.deleteById(id);

        return "redirect:/recruiter/dashboard";
    }

   
    @GetMapping("/edit-job/{id}")
    public String editJob(@PathVariable int id, Model model) {

        Job job = jobRepo.findById(id).orElse(null);

        model.addAttribute("job", job);

        return "recruiter/edit-job";
    }

    
    @PostMapping("/update-job")
    public String updateJob(@ModelAttribute Job job) {

        jobRepo.save(job);

        return "redirect:/recruiter/dashboard";
    }
    @GetMapping("/recruiter/applications")
    public String recruiterApplications(Model model) {

        model.addAttribute("applications", applicationRepo.findAll());

        return "recruiter/applications";
    }
    @GetMapping("/approve/{id}")
    public String approve(@PathVariable int id){

        Application app = applicationRepo.findById(id).orElse(null);

        if(app!=null){

            app.setStatus("Approved");

            applicationRepo.save(app);
        }

        return "redirect:/applications";
    }
    @GetMapping("/reject/{id}")
    public String reject(@PathVariable int id){

        Application app = applicationRepo.findById(id).orElse(null);

        if(app!=null){

            app.setStatus("Rejected");

            applicationRepo.save(app);
        }

        return "redirect:/recruiter/applications";
    }

}
