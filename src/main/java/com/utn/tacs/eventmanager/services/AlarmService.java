package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.errors.AlarmExistException;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {

    @Autowired
    private AlarmRepository alarmRepository;

    public void createAlarm(Alarm alarm) throws CustomException {
        if (alarmRepository.exists(Example.of(alarm))) {
            throw new AlarmExistException(alarm.getName());
        }
        alarmRepository.save(alarm);

    }

    public List<Alarm> getAlarms() {
        return alarmRepository.findAll();
    }
}
