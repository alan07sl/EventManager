package com.utn.tacs.eventmanager.authentication.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class LogicalDeleteableBean extends BaseBean {
	private Boolean active;
}