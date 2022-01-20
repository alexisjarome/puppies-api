package com.assignment.puppies.rest.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.assignment.puppies.domains.User;
import com.assignment.puppies.repositories.UserRepository;
import com.assignment.puppies.securities.JwtUtil;
import com.assignment.puppies.securities.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserSecurityService userSecurityServiceMock;

	@MockBean
	private JwtUtil jwtUtilMock;

	@MockBean
	private UserRepository repositoyMock;

	@Test
	public void testGetUser() throws Exception {
		User testUser = new User("foofirst", "foolast", "fooemail", "foopwd");
		testUser.setId(1l);
		when(repositoyMock.findById(1l)).thenReturn(Optional.of(testUser));
		MvcResult result = mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse().getContentAsString().equals(
				"{\"id\":1,\"firstName\":\"foofirst\",\"lastName\":\"foolast\",\"email\":\"foopwd\",\"name\":\"foofirst foolast\"}"));
	}

	@Test
	public void testGetUserNotFound() throws Exception {
		when(repositoyMock.findById(10l)).thenReturn(Optional.empty());
		MvcResult result = mockMvc.perform(get("/users/10")).andDo(print()).andExpect(status().isNotFound()).andReturn();

		assertThat(result.getResponse().getContentAsString().equals("Could not found entity 10"));
	}

	@Test
	public void testRegister() throws Exception {
		User registerUser = new User("regfirst", "reglast", "regemail", "regpwd");
		User saveUser = new User("regfirst", "reglast", "regemail", "regpwd");
		saveUser.setId(2L);

		String registerUserJson = new ObjectMapper().writeValueAsString(registerUser);

		when(repositoyMock.save(Mockito.eq(registerUser))).thenReturn(saveUser);

		MvcResult result = mockMvc
				.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(registerUserJson))
				.andDo(print()).andExpect(status().isCreated()).andReturn();
		assertThat(result.getResponse().getContentAsString().equals(
				"{\"id\":2,\"firstName\":\"regfirst\",\"lastName\":\"reglast\",\"email\":\"regpwd\",\"name\":\"regfirst reglast\"}"));
	}
}
