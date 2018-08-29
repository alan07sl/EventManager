package com.utn.tacs.eventmanager.controller.dto;

import lombok.Data;

@Data
public class UpdateAdminUserRequest extends CreateAdminUserRequest {
	private Long id;
}