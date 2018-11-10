package com.utn.tacs.eventmanager.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Alarm {

	private @Id
	String id;
	private String name;
	private String criteria;
	private String userId;

	public Alarm(String name, String criteria, String user) {
		this.name = name;
		this.criteria = criteria;
		this.userId = user;
	}

	public Alarm(String name, String user) {
		this.name = name;
		this.userId = user;
	}

}
