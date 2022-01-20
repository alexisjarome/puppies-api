package com.assignment.puppies.securities;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.assignment.puppies.domains.User;
import com.assignment.puppies.repositories.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByEmail(username);
		return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), Collections.EMPTY_LIST);
	}

}
