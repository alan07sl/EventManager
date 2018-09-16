package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;

/**
 * Will save the last 20 interesting events for the user.
 */
@Data
@Entity
public class Event {

	private @Id
	@GeneratedValue
	Long id;
	private String name;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "F_USER_ID", referencedColumnName = "ID")
	private User user;

	public Event(String name, User user) {
		this.name = name;
		this.user = user;
	}
}
