package com.assignment.puppies.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.puppies.domains.User;
import com.assignment.puppies.exceptions.NotFoundException;
import com.assignment.puppies.repositories.UserRepository;

@RestController
public class UserRestController {
	
	Logger log = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/users")
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	@GetMapping("/users/{userId}")
	public User get(@PathVariable Long userId) {
		User u = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
		return u;
	}
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {		
		log.info("Registering user {}", user.getEmail());
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	}

}
