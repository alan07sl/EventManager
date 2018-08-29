package com.utn.tacs.eventmanager.authentication.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User extends LogicalDeleteableBean {

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
	private Date lastLogin;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;
	@NotNull
	private Boolean locked;
	@NotNull
	private Integer badLogin;

	public void addBadLogin() {
		this.badLogin = badLogin+1;
	}

	public void restoreBadLogin() {
		this.badLogin=0;
	}
}
