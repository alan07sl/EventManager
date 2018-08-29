package com.utn.tacs.eventmanager.authentication.service.impl;

import com.utn.tacs.eventmanager.authentication.facade.AuthenticationFacade;
import com.utn.tacs.eventmanager.authentication.model.AppLevelEnum;
import com.utn.tacs.eventmanager.authentication.model.Role;
import com.utn.tacs.eventmanager.authentication.model.User;
import com.utn.tacs.eventmanager.authentication.service.RoleService;
import com.utn.tacs.eventmanager.authentication.service.UserService;
import com.utn.tacs.eventmanager.dao.UserDao;
import com.utn.tacs.eventmanager.exception.RoleNotFoundException;
import com.utn.tacs.eventmanager.exception.UserExistException;
import com.utn.tacs.eventmanager.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends LogicalDeleteableBeanService<User> implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final String SYSTEM_ADMINISTRATOR = "system_administrator";
	private static final String USER_APPLICATION = "user_application";
	private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthenticationFacade authenticationFacade;
	@Autowired
	private RoleService roleService;

	@Override
	public List<User> readAllActiveUsers() {
		return userDao.findAllByActiveIsTrue();
	}

	@Override
	public List<User> readAllUsers() {
		return userDao.findAll();
	}

	@Override
	public Collection<User> readAllAdministratorUsers(Boolean active) {
		return findUserByAppLevelAndActive(active, AppLevelEnum.ADMINISTRATOR);
	}

	@Override
	public Collection<User> readAllApplicationUsers(Boolean active) {
		return findUserByAppLevelAndActive(active, AppLevelEnum.END_USER);
	}

	public Optional<User> readUserForLogin(String userEmail) {
		return userDao.findUserByActiveIsTrueAndEmailAndLockedIsFalse(userEmail);
	}

	@Override
	public Optional<User> readUserByEmail(String email) {
		return readOptUserByEmail(email);
	}

	@Override
	public Optional<User> readOptUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	public User createUser(User user) {
		if (userDao.findUserByEmail(user.getEmail()).isPresent()) {
			throw new UserExistException(user.getEmail());
		}
		return userDao.save(user);
	}

	@Override
	public User updateUser(User user) {
		Optional<User> optUser = userDao.findUserByEmail(user.getEmail());
		if (optUser.isPresent() && !optUser.get().getId().equals(user.getId())) {
			throw new UserExistException(user.getEmail());
		}
		return userDao.save(prepareToUpdate(user));
	}

	@Override
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public void deleteUser(String uuid) {
		userDao.deleteByUuid(uuid);
	}

	@Override
	public Optional<User> readUserById(@NotNull Long id) {
		return userDao.findById(id);
	}

	@Override
	public User createAdminUser(User user) {
		return createDefaultProfileUser(user, SYSTEM_ADMINISTRATOR);
	}

	@Override
	public Optional<User> getLogedUser() {
		Authentication autorization = Optional.ofNullable(authenticationFacade.getAuthentication()).orElseThrow(() -> new UnauthorizedUserException("The user is not logged"));
		return readOptUserByEmail((String) autorization.getPrincipal());
	}

	@Override
	public User createApplicationUser(User user) {
		return createDefaultProfileUser(user, USER_APPLICATION);
	}


	protected User createDefaultProfileUser(User user, String defaultRole) {
		String formatedRole = defaultRole.toLowerCase().trim();
		Role role = roleService.readRoleByName(formatedRole).orElseThrow(() -> {
			LOG.error(String.format("Error when I trying to read the %s role/profile ", formatedRole));
			return new RoleNotFoundException(formatedRole);
		});
		user.setRoles(Arrays.asList(role));
		return createUser(user);
	}

	@Override
	protected User prepareToUpdate(User user) {
		Optional<User> optionalUser = userDao.findById(user.getId());
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(user.getEmail());
		}
		optionalUser.get().setEmail(user.getEmail());
		optionalUser.get().setName(user.getName());
		if(user.getPassword() != null && !optionalUser.get().getPassword().equals(user.getPassword())) {
			optionalUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
		}
		return super.prepareToUpdate(optionalUser.get());
	}

	private Collection<User> findUserByAppLevelAndActive(Boolean active, AppLevelEnum level) {
		return active == null ? userDao.findAllByRoles(level) : userDao.findAllByRolesAndActive(active, level);
	}

}