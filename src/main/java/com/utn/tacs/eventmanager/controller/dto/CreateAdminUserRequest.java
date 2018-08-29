package com.utn.tacs.eventmanager.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateAdminUserRequest {

	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@NotEmpty
	private String lastName;
	@NotNull
	@NotEmpty
	private String email;
	@NotNull
	@NotEmpty
	private String password;
	@NotNull
	@NotEmpty
	private String confirmPassword;
	@NotNull
	@NotEmpty
	private List<Long> roles;
}
