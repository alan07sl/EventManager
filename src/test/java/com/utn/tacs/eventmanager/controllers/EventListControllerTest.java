package com.utn.tacs.eventmanager.controllers;




import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.EventDTO;
import com.utn.tacs.eventmanager.controllers.dto.EventListDTO;
import com.utn.tacs.eventmanager.controllers.dto.ListDTO;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
		mockMvc.perform(get("/events_lists/events")).andExpect(status().isOk());

	}
	@Test
	public void shouldGetCommonsEvents() throws Exception {
		mockMvc.perform(get("/events_lists/match?eventListId1=1&eventListId2=2"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

	}

	@Test
	public void shouldSearchEventList() throws Exception {
        EventListDTO result1 = new EventListDTO();
        result1.setName("r1");
        result1.setId(1);

		mockMvc.perform(get("/events_lists")).andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").isNumber())
                .andExpect(jsonPath("$.pageCount").isNumber())
                .andExpect(jsonPath("$.resultCount").isNumber())
                .andExpect(jsonPath("$.next").isString())
                .andExpect(jsonPath("$.prev").isString())
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.result[:1].id").value(result1.getId()))
                .andExpect(jsonPath("$.result[:1].name").value(result1.getName()));
	}
  
    @Test
    public void shouldGetEventsFromEventList() throws Exception {
        mockMvc.perform(get("/events_lists/1/events"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[:1].id").value(1))
                .andExpect(jsonPath("$.[1:2].id").value(2))
                .andExpect(status().isOk());
    }
}
