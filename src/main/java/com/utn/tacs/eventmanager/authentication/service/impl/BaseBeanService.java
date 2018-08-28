package com.utn.tacs.eventmanager.authentication.service.impl;

import com.utn.tacs.eventmanager.authentication.model.BaseBean;

import java.util.UUID;

public abstract class BaseBeanService<T extends BaseBean> extends TimestampBeanService<T> {

	protected T prepareToCreate(T baseBean) {
		baseBean.setUuid(UUID.randomUUID().toString());
		return super.prepareToCreate(baseBean);
	}

}