package com.assignment.puppies.securities;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	
	public String generateToken(UserDetails userDetails) {
		String email = userDetails.getUsername();
		String token = buildToken(email);
		
		return token;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		if (token == null || userDetails == null) {
			return false;
		}
		
		String email = userDetails.getUsername();
		String userToken = buildToken(email);
		return token.equals(userToken);
	}
	
	private String buildToken(String email) {
		return email.replace(".", "dot").replace("@", "at");
	}
	
	public String extractEmail(String token) {
		return token.replace("dot", ".").replace("at", "@");
	}

}
