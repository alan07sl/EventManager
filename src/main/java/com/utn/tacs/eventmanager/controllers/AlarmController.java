package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.AlarmDTO;
import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.AlarmService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/alarms")
public class AlarmController {

	@Autowired
	private MapperFacade orikaMapper;

	@Autowired
	private AlarmService alarmService;

	@PostMapping
	public ResponseEntity<Object> createAlarm(@Valid @RequestBody AlarmDTO alarm) throws CustomException {
		alarmService.createAlarm(orikaMapper.map(alarm, Alarm.class));
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
