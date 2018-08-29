package com.utn.tacs.eventmanager.authentication.service;

import com.utn.tacs.eventmanager.authentication.model.User;
import com.utn.tacs.eventmanager.exception.NotFoundException;
import com.utn.tacs.eventmanager.exception.UserExistException;
import com.utn.tacs.eventmanager.exception.UserNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface UserService {

	List<User> readAllActiveUsers();

	List<User> readAllUsers();

	Collection<User> readAllAdministratorUsers(Boolean active);

	/**
	 * Read all users
	 * @param active
	 * @return
	 */
	Collection<User> readAllApplicationUsers(Boolean active);

	Optional<User> readUserForLogin(String email);

	Optional<User> readUserByEmail(String email) throws UserNotFoundException;

	Optional<User> readOptUserByEmail(String email);

	User createApplicationUser(User user) throws UserExistException;

	User createUser(User user);

	User updateUser(User user) throws UserExistException, NotFoundException;

	void deleteUser(Long id);

	void deleteUser(String uuid);

	Optional<User> readUserById(@NotNull Long id);

	User createAdminUser(User user);

	Optional<User> getLogedUser();
}