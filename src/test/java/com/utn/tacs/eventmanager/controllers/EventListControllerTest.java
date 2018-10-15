package com.utn.tacs.eventmanager.controllers;




import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.EventDTO;
import com.utn.tacs.eventmanager.controllers.dto.EventListDTO;
import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.EventListService;
import com.utn.tacs.eventmanager.services.EventbriteService;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;


@RunWith(SpringRunner.class)
@WebMvcTest(value = EventListController.class, secure = false)
public class EventListControllerTest {

	@Autowired
	private MockMvc mockMvc;

    @Autowired
    private MapperFacade orikaMapper;

    @MockBean
    private EventbriteService eventbriteService;

    @MockBean
    private EventListService eventListService;

	@MockBean
	private UserService userService;

	@Test
	public void shouldCreateEventList() throws Exception {
		EventListDTO eventList = new EventListDTO();
		eventList.setName("MyList");

        Mockito.when(userService.findCurrentUser()).thenReturn(new User("",""));

		mockMvc.perform(post("/events_lists")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(eventList))).andExpect(status().isCreated());
	}

	@Test
	public void shouldAddEventToList() throws Exception {
		EventDTO event = new EventDTO();
		event.setId(1L);

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
		mockMvc.perform(get("/events_lists/events?from=2018-09-01")).andExpect(status().isOk());

	}
	@Test
	public void shouldGetCommonsEvents() throws CustomException, Exception {

        EventList eventList = new EventList("a");
        Set<Long> events = new HashSet<>();
        events.addAll(Arrays.asList(1L,2L,3L,4L));
        eventList.setEvents(events);

        Mockito.when(eventListService.findById(1)).thenReturn(eventList);

        EventList eventList2 = new EventList("b");
        eventList2.setEvents(events);

        Mockito.when(eventListService.findById(2)).thenReturn(eventList2);

		mockMvc.perform(get("/events_lists/match?eventListId1=1&eventListId2=2"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"));

	}

	@Test
	public void shouldSearchEventList() throws Exception {
        EventList result1 = new EventList("r1");
        result1.setId(1L);

        Page<EventList> result = new PageImpl<>(Arrays.asList(result1));

        Mockito.when(eventListService.searchPaginated("",2,10)).thenReturn(result);

		mockMvc.perform(get("/events_lists?page=2")).andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").isNumber())
                .andExpect(jsonPath("$.pageCount").isNumber())
                .andExpect(jsonPath("$.resultCount").isNumber())
                .andExpect(jsonPath("$.next").isEmpty())
                .andExpect(jsonPath("$.prev").isString())
                .andExpect(jsonPath("$.result").isArray());
	}
  
    @Test
    public void shouldGetEventsFromEventList() throws Exception, CustomException {

	    EventList eventList = new EventList("a");
	    Set<Long> events = new HashSet<>();
	    events.addAll(Arrays.asList(1L,2L,3L,4L));
	    eventList.setEvents(events);

        Mockito.when(eventListService.findById(1)).thenReturn(eventList);

        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> event1 = new HashMap<>();
        event1.put("id",1);

        Map<String,Object> event2 = new HashMap<>();
        event2.put("id",2);

        list.add(event1);
        list.add(event2);

        Mockito.when(eventbriteService.getEvents(events)).thenReturn(list);

        mockMvc.perform(get("/events_lists/1/events"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[:1].id").value(1))
                .andExpect(jsonPath("$.[1:2].id").value(2))
                .andExpect(status().isOk());
    }
}
