package com.utn.tacs.eventmanager.authentication.facade;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	Authentication getAuthentication();
}