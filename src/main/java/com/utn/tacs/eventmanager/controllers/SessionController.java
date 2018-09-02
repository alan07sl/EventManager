package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.SessionDTO;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody UserDTO user) {
        SessionDTO session = new SessionDTO();
        session.setToken("TOKEN");
        return new ResponseEntity<>(session,HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteSession(HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
