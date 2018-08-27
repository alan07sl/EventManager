package com.utn.tacs.eventmanager;

import com.utn.tacs.eventmanager.controllers.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateUser() throws Exception {
		mockMvc.perform(post("/users")).andExpect(status().isCreated());
	}

}
