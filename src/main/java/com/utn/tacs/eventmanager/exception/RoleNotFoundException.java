package com.utn.tacs.eventmanager.exception;

import com.utn.tacs.eventmanager.authentication.model.Role;

public class RoleNotFoundException extends NotFoundException {
	public RoleNotFoundException(Role role) {
		super("001", "", null, "", new String[]{role.getName()});
	}

	public RoleNotFoundException(String name) {
		super("001", "", null, "", new String[]{name});
	}
}