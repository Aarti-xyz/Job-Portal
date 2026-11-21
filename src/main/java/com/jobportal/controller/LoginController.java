package com.jobportal.controller;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;

@Controller
public class LoginController{
	@Autowired
	private UserRepository repo;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email,
	                    @RequestParam String password,
	                    HttpSession session) {

	    User user = repo.findByEmailAndPassword(email, password);

	    if (user != null) {

	        session.setAttribute("user", user);
	        session.setAttribute("email", user.getEmail());
	        
	        System.out.println("Login User = " + user.getName());

	        return "redirect:/user/dashboard";
	    }

	    return "login";
	}
}
	