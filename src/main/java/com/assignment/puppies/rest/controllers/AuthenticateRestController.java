package com.assignment.puppies.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.puppies.beans.AuthenticationRequest;
import com.assignment.puppies.beans.AuthenticationResponse;
import com.assignment.puppies.securities.JwtUtil;
import com.assignment.puppies.securities.UserSecurityService;

@RestController
public class AuthenticateRestController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	 @PostMapping("/authenticate")
	 public ResponseEntity authenticate(@RequestBody AuthenticationRequest authRequest) throws Exception {

			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

			} catch (BadCredentialsException ex) {
				throw new BadCredentialsException("Incorrect email or password", ex);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}

			
		 	

		 UserDetails userDetails = userSecurityService.loadUserByUsername(authRequest.getEmail());

		 return ResponseEntity.ok(new AuthenticationResponse(jwtUtil.generateToken(userDetails)));
	 }
}
