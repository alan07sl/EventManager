package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.EventListService;
import com.utn.tacs.eventmanager.services.EventbriteService;
import com.utn.tacs.eventmanager.services.UserService;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import com.utn.tacs.eventmanager.services.dto.PaginatedDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EventController.class, secure = false)
public class EventControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @MockBean
    private EventbriteService eventbriteService;

    @MockBean
    private EventListService eventListService;

	@Test
    public void shouldGet() throws Exception {
        mockMvc.perform(get("/events/1/users"))
                .andExpect(jsonPath("$.amount").isNumber())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetEvents() throws Throwable {
        PaginatedDTO pagination = new PaginatedDTO(20, 2, 10,20, true);

        EventsResponseDTO response = new EventsResponseDTO(pagination, new ArrayList<>());

        Mockito.when(eventbriteService.getEvents("2", "")).thenReturn(response);

        mockMvc.perform(get("/events?page=2"))
                .andExpect(jsonPath("$.result").isArray())
                .andExpect(jsonPath("$.pageNumber").isNumber())
                .andExpect(jsonPath("$.pageCount").isNumber())
                .andExpect(jsonPath("$.resultCount").isNumber())
                .andExpect(jsonPath("$.next").isString())
                .andExpect(jsonPath("$.prev").isString())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailGetEvents() throws Throwable {

        Mockito.when(eventbriteService.getEvents("2", "")).thenThrow(new CustomException("","", HttpStatus.INTERNAL_SERVER_ERROR));

        mockMvc.perform(get("/events?page=2"))
                .andExpect(jsonPath("$.status").isNumber())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.description").isString())
                .andExpect(status().isInternalServerError());
    }
}

