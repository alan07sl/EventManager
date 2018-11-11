package com.utn.tacs.eventmanager.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * Will save the last 20 interesting events for the user.
 */
@Data
public class Event {

	private @Id
	String id;
	private String name;
	private String userId;

	public Event(String name, String user) {
		this.name = name;
		this.userId = user;
	}
}
