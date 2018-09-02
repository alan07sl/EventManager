package com.utn.tacs.eventmanager.controllers;

import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;


@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateSession() throws Exception {
		UserDTO user = new UserDTO();
		user.setUsername("Martin");
		user.setPassword("123456789");

		mockMvc.perform(post("/sessions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(user))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isNotEmpty());
	}

    @Test
    public void shouldDeleteSession() throws Exception {
        mockMvc.perform(delete("/sessions")).andExpect(status().isOk());
    }

}
