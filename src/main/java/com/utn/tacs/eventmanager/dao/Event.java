package com.utn.tacs.eventmanager.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * Will save the last 20 interesting events for the user.
 */
@Data
public class Event {

	private @Id String id;
	private Long eventId;
	private String userId;
	private String alarmId;

	public Event(Long eventId, String userId, String alarmId) {
		this.eventId = eventId;
		this.userId = userId;
		this.alarmId = alarmId;
	}

    public Event(String userId, String alarmId) {
        this.userId = userId;
        this.alarmId = alarmId;
    }
}
