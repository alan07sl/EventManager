package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.dao.EventList;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.AlarmExistException;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.repositories.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void deleteAlarm(Long id) {
        alarmRepository.deleteById(id);
    }

    public Page<Alarm> searchPaginated(String name, User user, Integer page, Integer size) {
        Alarm alarm = new Alarm(name.length() > 0 ? name : null, user);
        Pageable pageable = new PageRequest(page - 1, size);
        return alarmRepository.findAll(Example.of(alarm), pageable);
    }

    public List<Alarm> getAlarms() {
        return alarmRepository.findAll();
    }
}
