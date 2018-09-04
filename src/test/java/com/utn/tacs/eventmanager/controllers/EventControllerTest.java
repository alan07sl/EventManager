package com.utn.tacs.eventmanager.controllers;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
    public void shouldGet() throws Exception {
        mockMvc.perform(get("/events/1/users"))
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetEvents() throws Exception {
        mockMvc.perform(get("/events")) // Llama al endpoint
                //.contentType(MediaType.APPLICATION_JSON)
                //.content(new Gson().toJson())) // Le pasa algo (un body si hace falta)
                .andExpect(status().isOk()); //Chequea la rta
    }
}

