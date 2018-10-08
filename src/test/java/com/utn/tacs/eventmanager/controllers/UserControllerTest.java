package com.utn.tacs.eventmanager.controllers;

import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure=false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MapperFacade orikaMapper;

	@MockBean
	private UserService userService;

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
	public void shouldGetUser() throws Exception {
		Page<User> result = new PageImpl<>(Arrays.asList());

		Mockito.when(userService.searchPaginated(new User(null,null),1, 10)).thenReturn(result);

		mockMvc.perform(get("/users"))
				.andExpect(status().isOk());
	}

    @Test
    public void shouldGetUserId() throws Exception, CustomException {

	    User user = new User("aaa", null);
	    user.setAlarms(new ArrayList<>());
        user.setEventsLists(new ArrayList<>());

		Mockito.when(userService.findById(1)).thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

}
