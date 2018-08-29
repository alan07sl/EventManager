package com.utn.tacs.eventmanager.authentication.service.impl;

import com.utn.tacs.eventmanager.authentication.model.LogicalDeleteableBean;
import lombok.Data;

@Data
public abstract class LogicalDeleteableBeanService<T extends LogicalDeleteableBean> extends BaseBeanService<T> {
	protected T prepareToCreate(T baseBean) {
		baseBean.setActive(true);
		return super.prepareToCreate(baseBean);
	}
}