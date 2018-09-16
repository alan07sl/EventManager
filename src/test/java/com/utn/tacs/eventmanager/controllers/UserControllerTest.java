package com.utn.tacs.eventmanager.controllers;

import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.errors.UserExistException;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private MapperFacade orikaMapper;

	@Test
	public void shouldCreateUser() throws CustomException,Exception {
		UserDTO user = new UserDTO();
		user.setUsername("Martin");
		user.setPassword("123456789");

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(user))).andExpect(status().isCreated());
	}

	@Test
	public void shouldNotCreateUserTwice() throws CustomException,Exception {
		UserDTO user = new UserDTO();
		user.setUsername("Martin");
		user.setPassword("123456789");

		doNothing().doThrow(new UserExistException(user.getUsername())).when(userService).createUser(ArgumentMatchers.any());

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(user))).andExpect(status().isCreated());

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(user))).andExpect(status().isBadRequest());
	}


	@Test
	public void shouldGetUser() throws Exception {
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk());
	}

    @Test
    public void shouldGetUserId() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

}
