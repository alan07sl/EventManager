package com.utn.tacs.eventmanager.controller;

import com.utn.tacs.eventmanager.authentication.model.User;
import com.utn.tacs.eventmanager.authentication.service.UserService;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MapperFacade orikaMapper;

	@MockBean
	private UserService userService;

	@Test
	@WithMockUser()
	public void getProfileForLoggedUser() throws Exception {
		when(userService.getLogedUser()).thenReturn(Optional.of(new User()));
		mockMvc.perform(get("/api/user/profile"))
		.andExpect(status().isOk());
	}

	@Test
	@WithMockUser()
	public void getProfileForLoggedUserNotFound() throws Exception {
		mockMvc.perform(get("/api/user/profile")).andExpect(status().isNotFound());
	}

	@Test
	@WithAnonymousUser
	public void getProfileForNotLoggedUser() throws Exception {
		mockMvc.perform(get("/api/user/profile")).andExpect(status().isUnauthorized());
	}

}