package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.AlarmDTO;
import com.utn.tacs.eventmanager.controllers.dto.ListDTO;
import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.AlarmService;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/alarms")
public class AlarmController {

	@Autowired
	private MapperFacade orikaMapper;

	@Autowired
	private AlarmService alarmService;

    @Autowired
    private UserService userService;

	@PostMapping
	public ResponseEntity<Object> createAlarm(@Valid @RequestBody AlarmDTO alarm) throws CustomException {
	    Alarm alarmDAO = orikaMapper.map(alarm, Alarm.class);
	    alarmDAO.setUser(userService.findCurrentUser());
	    alarmService.createAlarm(alarmDAO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAlarm(@PathVariable Integer id) {
        alarmService.deleteAlarm(id.longValue());
        return new ResponseEntity<>(HttpStatus.OK);
    }

	@GetMapping
	public ResponseEntity<ListDTO<AlarmDTO>> getAlarms(@RequestParam(value = "name", required = false, defaultValue = "") String name,
																@RequestParam(value = "page", defaultValue = "1") Integer page,
																@RequestParam(value = "size", defaultValue = "10") Integer size ) {
	    User user = userService.findCurrentUser();
		Page<Alarm> result = alarmService.searchPaginated(name, user, page, size);

		ListDTO<AlarmDTO> list = new ListDTO<>();
		list.setPageNumber(page);
		list.setPageCount(result.getTotalPages());
		list.setResultCount(result.getTotalElements());
		list.setResult(result.getContent().stream().map((Alarm e) -> orikaMapper.map(e, AlarmDTO.class)).collect(Collectors.toList()));
		list.setNext(result.hasNext() ? "/alarms?page="+ (list.getPageNumber() + 1) + "&name=" + name + "&size=" + size : null);
		list.setPrev(list.getPageNumber() > 1 ? "/alarms?page="+ (list.getPageNumber() - 1) + "&name=" + name + "&size=" + size : null);

		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
