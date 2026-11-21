package com.jobportal.service;
 import org.springframework.*;
 import com.jobportal.entity.User;
 import com.jobportal.repository.UserRepository;
public class UserService {
	
	private final UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public User saveUser(User user) {
		return userRepository.save(user);
	}
}

	
