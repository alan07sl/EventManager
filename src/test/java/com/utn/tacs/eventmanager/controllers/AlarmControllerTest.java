package com.utn.tacs.eventmanager.controllers;

import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.AlarmDTO;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.services.AlarmService;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AlarmController.class, secure = false)
public class AlarmControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AlarmService alarmService;

	@MockBean
	private UserService userService;

	@Autowired
	private MapperFacade orikaMapper;

	@Test
	public void shouldCreateAlarm() throws Exception {

		AlarmDTO alarmDTO = new AlarmDTO();
		alarmDTO.setName("Alarma de test");
		alarmDTO.setCriteria("unCriterio");

		Mockito.when(userService.findCurrentUser()).thenReturn(new User("aaa"));

		mockMvc.perform(post("/alarms")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(alarmDTO))).andExpect(status().isCreated());
	}

	@Test
	public void emptyRequestBody() throws Exception {
		mockMvc.perform(post("/alarms")).andExpect(status().isBadRequest());
	}

}