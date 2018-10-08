package com.utn.tacs.eventmanager.job;

import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.repositories.EventRepository;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import com.utn.tacs.eventmanager.services.AlarmService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlarmSchedulerTest {

	@Autowired
	private AlarmScheduler alarmScheduler;
	@MockBean
	private AlarmService alarmService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private EventRepository eventRepository;

	@Test
	public void shouldAddEventsTest(){
		//Given
		User user = new User("alan", "1234");
		userRepository.save(user);
		Alarm alarm = new Alarm("Alarma de Alan", null, user);

		when(alarmService.getAlarms()).thenReturn(Arrays.asList(alarm));

		//Do
		alarmScheduler.activateAlarms();

		//Assert
		assertThat("No creo eventos para mostrar", eventRepository.findAll().size() > 0);
	}

	@Test
	public void shouldAddEventsWithCriteriaTest(){
		//Given
		User user = new User("alan", "1234");
		userRepository.save(user);
		Alarm alarm = new Alarm("Alarma de Alan", "StrangerThings", user);

		when(alarmService.getAlarms()).thenReturn(Arrays.asList(alarm));

		//Do
		alarmScheduler.activateAlarms();

		//Assert
		assertThat("No creo eventos para mostrar", eventRepository.findAll().size() > 0);
		assertThat("No trajo eventos con el criterio", eventRepository.findAll().stream().anyMatch((event -> event.getName().contains("Stranger"))), Is.is(true));
	}
}