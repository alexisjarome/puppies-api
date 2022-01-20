package com.assignment.puppies.securities;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		String requestToken = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			requestToken = authHeader.substring(7);
			String email = jwtUtil.extractEmail(requestToken);
			
			UserDetails user = userSecurityService.loadUserByUsername(email);
			
			
			if (SecurityContextHolder.getContext().getAuthentication() == null && jwtUtil.validateToken(requestToken, user)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
  
		}
		
		filterChain.doFilter(request, response);
		
	}

}
