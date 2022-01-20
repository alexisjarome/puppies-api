package com.assignment.puppies.rest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.assignment.puppies.beans.AuthenticationRequest;
import com.assignment.puppies.repositories.UserRepository;
import com.assignment.puppies.securities.JwtUtil;
import com.assignment.puppies.securities.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@WebMvcTest(AuthenticateRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticateRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserSecurityService userSecurityServiceMock;

	@MockBean
	private JwtUtil jwtUtilMock;

	@MockBean
	private UserRepository repositoyMock;
	
	@MockBean
	private AuthenticationManager authenticationManagerMock;
	
	@Test
	public void testAuthenticate() throws Exception {
		AuthenticationRequest req = new AuthenticationRequest();
		req.setEmail("foo");
		req.setPassword("bar");
		
		UserDetails userDetails = userDetailsMock(req.getEmail(), req.getPassword());
		when(userSecurityServiceMock.loadUserByUsername(Mockito.eq("foo"))).thenReturn(userDetails);
		when(jwtUtilMock.generateToken(Mockito.eq(userDetails))).thenReturn("testokennnn");
		
		String reqJson = new ObjectMapper().writeValueAsString(req);

		MvcResult result = mockMvc
				.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(reqJson))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		
		assertThat(result.getResponse().getContentAsString().equals("{\"token\":\"testokennnn\"}"));
	}
	
	@Test
	public void testAuthenticateFail() throws Exception {
		AuthenticationRequest req = new AuthenticationRequest();
		req.setEmail("foo");
		req.setPassword("bar");
		
		String reqJson = new ObjectMapper().writeValueAsString(req);
		
		when(authenticationManagerMock.authenticate(Mockito.any())).thenThrow(BadCredentialsException.class);
		
		MvcResult result = mockMvc
				.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(reqJson))
				.andDo(print()).andExpect(status().isForbidden()).andReturn();
		
		assertThat(result.getResponse().getContentAsString().equals("Incorrect email or password"));		
	}
	
	private static UserDetails userDetailsMock(String email, String password) {
		return new UserDetails() {
			
			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isCredentialsNonExpired() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAccountNonLocked() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAccountNonExpired() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return email;
			}
			
			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return password;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
