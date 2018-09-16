package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.AlarmExistException;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.repositories.AlarmRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlarmServiceTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private AlarmRepository alarmRepository;

    @Test
    public void shouldCreateAlarm() throws CustomException {
        User user = new User("alan", "1234");
        alarmService.createAlarm(new Alarm("Alarma de Alan", null, user));
        assertThat(alarmRepository.exists(Example.of(new Alarm("Alarma de Alan", null, user))), equalTo(true));
    }

    @Test
    public void shouldFailCreateAlarmBecauseAlreadyExist() throws CustomException {
        User user = new User("alan", "1234");
        Alarm alarm = new Alarm("Alarma de Alan", null, user);
        alarmService.createAlarm(alarm);
        try{
            alarmService.createAlarm(alarm);
            assertThat("Should fail because alarm already exist", false);
        } catch (AlarmExistException e) {
            assertThat("Alarm already exist", true);
        }
    }
}
