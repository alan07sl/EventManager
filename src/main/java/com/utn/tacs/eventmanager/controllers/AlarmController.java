package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.AlarmDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/alarms")
public class AlarmController {

	@PostMapping
	public ResponseEntity<Object> createAlarm(@RequestBody AlarmDTO alarm) {
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
