package com.utn.tacs.eventmanager.dao;

import com.utn.tacs.eventmanager.authentication.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDao extends BaseBeanDao<Role> {

	List<Role> findAllByNameLike(String name);

	Optional<Role> findByNormalizedName(String name);

	void deleteByName(String name);

}