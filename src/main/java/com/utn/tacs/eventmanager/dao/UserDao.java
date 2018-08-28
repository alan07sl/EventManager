package com.utn.tacs.eventmanager.dao;

import com.utn.tacs.eventmanager.authentication.model.AppLevelEnum;
import com.utn.tacs.eventmanager.authentication.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UserDao extends LogicalDeleteableBeanDao<User> {

	Optional<User> findUserByActiveIsTrueAndEmailAndLockedIsFalse(String email);

	Optional<User> findUserByEmail(String email);

	@Query(value = "	SELECT u FROM User" +
			"	INNER JOIN USER_ROLE ur ON ur.USER_ID = u.id" +
			"							INNER JOIN ROLE r ON (r.level = ?2 AND r.id= ur.ROLE_ID)", nativeQuery = true)
	Set<User> findAllByRoles(AppLevelEnum levelEnum);

	@Query(value = "	SELECT u FROM User" +
			"	INNER JOIN USER_ROLE ur ON (u.ACTIVE = ?1 AND ur.USER_ID = u.id)" +
			"							INNER JOIN ROLE r ON (r.level = ?2 AND r.id= ur.ROLE_ID)", nativeQuery = true)
	Set<User> findAllByRolesAndActive(Boolean active, AppLevelEnum levelEnum);

	@Query(value = "	SELECT u FROM User" +
			"	INNER JOIN USER_ROLE ur ON (u.ID = ?1 AND ur.USER_ID = u.id)" +
			"							INNER JOIN ROLE r ON (r.level = ?2 AND r.id= ur.ROLE_ID)", nativeQuery = true)
	Optional<User> findUserByIdAndRoles(Long id, AppLevelEnum levelEnum);

}
