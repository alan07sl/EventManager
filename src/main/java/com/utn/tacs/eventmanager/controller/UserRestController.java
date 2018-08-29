package com.utn.tacs.eventmanager.controller;

import com.utn.tacs.eventmanager.authentication.service.UserService;
import com.utn.tacs.eventmanager.controller.dto.UserProfileDTO;
import com.utn.tacs.eventmanager.exception.UserNotFoundException;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clase para obtener el perfil de un usuario logeado.
 */
@RestController
@RequestMapping("/api/user/profile")
public class UserRestController {
	@Autowired
	private MapperFacade orikaMapper;
	@Autowired
	private UserService userService;

	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public UserProfileDTO getProfile() {
		return orikaMapper.map(userService.getLogedUser().orElseThrow(()-> new UserNotFoundException("")), UserProfileDTO.class);
	}
}
