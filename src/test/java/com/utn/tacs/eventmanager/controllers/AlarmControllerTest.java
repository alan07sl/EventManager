package com.utn.tacs.eventmanager.controllers;

import com.google.gson.Gson;
import com.utn.tacs.eventmanager.controllers.dto.AlarmDTO;
import com.utn.tacs.eventmanager.security.JWTRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AlarmController.class, secure = false)
public class AlarmControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateAlarm() throws Exception {

		AlarmDTO alarmDTO = new AlarmDTO();
		alarmDTO.setName("Alarma de test");
		alarmDTO.setCriteria(new HashMap<String, String>(){
			{
				put("key1", "value1");
				put("key2", "value2");
			}
		});

		mockMvc.perform(post("/alarms")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(alarmDTO))).andExpect(status().isCreated());
	}

	@Test
	public void emptyRequestBody() throws Exception {
		mockMvc.perform(post("/alarms")).andExpect(status().isBadRequest());
	}

}