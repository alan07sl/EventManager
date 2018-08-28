package com.utn.tacs.eventmanager.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class NotFoundException extends BusinessException {

	public NotFoundException(String code, String messageCode, String[] messageArgs, String descriptionCode, String[] descriptionArgs) {
		super(code, messageCode, messageArgs, descriptionCode, descriptionArgs, HttpStatus.NOT_FOUND);
	}

}