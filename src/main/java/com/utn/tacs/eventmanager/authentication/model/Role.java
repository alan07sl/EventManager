package com.utn.tacs.eventmanager.authentication.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Role extends LogicalDeleteableBean {

	@NotNull
	@NotEmpty
	@Column(unique = true)
	private String name;
	@NotNull
	@NotEmpty
	private String description;
	@NotEmpty
	@NotNull
	@Column(unique = true)
	private String normalizedName;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission",
			joinColumns = @JoinColumn(name = "ROLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
	private List<Permission> permissions;
	@NotNull
	@Enumerated(EnumType.STRING)
	private AppLevelEnum level;

	public void setName(String name) {
		this.name = name;
		this.normalizedName = name.trim().toLowerCase();
	}
}