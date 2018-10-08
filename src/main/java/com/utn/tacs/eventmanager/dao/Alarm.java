package com.utn.tacs.eventmanager.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Alarm {

	private @Id
	@GeneratedValue
	Long id;
	@Column
	private String name;
	@Column
	private String criteria;
	@ManyToOne
	@JoinColumn(name = "F_USER_ID")
	private User user;

	public Alarm(String name, String criteria, User user) {
		this.name = name;
		this.criteria = criteria;
		this.user = user;
	}

}
