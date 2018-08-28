package com.utn.tacs.eventmanager.dao;

import com.utn.tacs.eventmanager.authentication.model.TimestampBean;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface TimestampDao<T extends TimestampBean> {
}