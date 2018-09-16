package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.ListDTO;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private MapperFacade orikaMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO user) throws CustomException {
        userService.createUser(orikaMapper.map(user, User.class));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ListDTO<UserDTO>> getUsers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size ){
        ListDTO<UserDTO> list = new ListDTO<>();
        list.setNext("/events?page=2");
        list.setPrev("/events?page=1");
        list.setPageCount(3);
        list.setPageNumber(page);
        list.setResultCount(100);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable String id) {
        UserDTO user = new UserDTO();
        user.setUsername("UserTest");
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}