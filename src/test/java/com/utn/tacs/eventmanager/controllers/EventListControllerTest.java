package com.utn.tacs.eventmanager.controllers;




import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.EventDTO;
import com.utn.tacs.eventmanager.controllers.dto.EventListDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;


@RunWith(SpringRunner.class)
@WebMvcTest(EventListController.class)
public class EventListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateEventList() throws Exception {
		EventListDTO eventList = new EventListDTO();
		eventList.setName("MyList");

		mockMvc.perform(post("/events_lists")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(eventList))).andExpect(status().isCreated());
	}

	@Test
	public void shouldAddEventToList() throws Exception {
		EventDTO event = new EventDTO();
		event.setId(1);

		mockMvc.perform(patch("/events_lists/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(event))).andExpect(status().isOk());
	}

	@Test
	public void shouldDeleteEventList() throws Exception {
		mockMvc.perform(delete("/events_lists/1")).andExpect(status().isOk());
	}

	@Test
	public void shouldUpdateEventList() throws Exception {
		EventListDTO eventList = new EventListDTO();
		eventList.setName("NewList");

		mockMvc.perform(put("/events_lists/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(eventList))).andExpect(status().isOk());
	}

	@Test
	public void shouldGetEvents() throws Exception {

		EventListDTO eventList = new EventListDTO();
		eventList.setName("NewList");

		mockMvc.perform(get("/events_lists/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(eventList))).andExpect(status().isOk());

	}
	@Test
	public void shouldGetCommonsEvents() throws Exception {
		EventListDTO eventList = new EventListDTO();
		eventList.setName("NewList");

		mockMvc.perform(get("/events_lists/match?eventListId=1&eventListId=2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(eventList))).andExpect(status().isOk());

	}
}
