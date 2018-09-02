package com.utn.tacs.eventmanager.controllers.dto;

import lombok.Data;

import java.util.Map;

@Data
public class AlarmDTO {

	private String name;
	private Map<String,String> criteria;

}
