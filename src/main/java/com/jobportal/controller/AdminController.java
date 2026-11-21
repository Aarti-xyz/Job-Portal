package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jobportal.entity.Admin;
import com.jobportal.repository.AdminRepository;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;

@Controller
public class AdminController{
	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JobRepository jobRepo;
	
	@Autowired
	private CompanyRepository companyRepo;

	
	@Autowired
	private ApplicationRepository applicationRepo;
	
	@GetMapping("/admin/login")
	public String adminLoginPage() {
		return "admin/login";
	}
	@PostMapping("/admin/login")
	public String adminLogin(@RequestParam String email,
	                         @RequestParam String password) {

	    Admin admin = adminRepo.findByEmailAndPassword(email, password);

	    if (admin != null) {
	        System.out.println("Login Success");
	        return "redirect:/admin/dashboard";
	    }

	    System.out.println("Login Failed");

	    return "admin/login";
	}
	@GetMapping("/admin/dashboard")
	public String dashboard() {
		return "admin/dashboard";
	}
	@GetMapping("/admin/users")
	public String viewUsers(Model model) {

	    model.addAttribute("users", userRepo.findAll());

	    return "admin/users";
	}

	

	@GetMapping("/admin/delete-user/{id}")
	public String deleteUser(@PathVariable int id) {

	    userRepo.deleteById(id);

	    return "redirect:/admin/users";
	}
	
	@GetMapping("/admin/jobs")
	public String viewJobs(Model model) {

	    model.addAttribute("jobs", jobRepo.findAll());

	    return "admin/jobs";
	}

	@GetMapping("/admin/delete-job/{id}")
	public String deleteJob(@PathVariable int id) {

	    jobRepo.deleteById(id);

	    return "redirect:/admin/jobs";
	}
	
	@GetMapping("/admin/reports")
	public String reports(Model model) {

	    model.addAttribute("totalUsers", userRepo.count());

	    model.addAttribute("totalJobs", jobRepo.count());

	    model.addAttribute("totalCompanies", companyRepo.count());

	    model.addAttribute("totalApplications", applicationRepo.count());

	    model.addAttribute("accepted",
	            applicationRepo.countByStatus("Accepted"));

	    model.addAttribute("rejected",
	            applicationRepo.countByStatus("Rejected"));

	    model.addAttribute("pending",
	            applicationRepo.countByStatus("Pending"));

	    return "admin/reports";
	}
	    
	    @GetMapping("/admin/applications")
	    public String viewApplications(Model model) {

	        model.addAttribute("applications", applicationRepo.findAll());

	        return "admin/applications";
	    
	}
}

	
