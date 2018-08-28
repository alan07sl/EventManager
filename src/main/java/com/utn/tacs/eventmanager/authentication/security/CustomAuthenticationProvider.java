package com.utn.tacs.eventmanager.authentication.security;

import com.utn.tacs.eventmanager.authentication.model.Permission;
import com.utn.tacs.eventmanager.authentication.model.User;
import com.utn.tacs.eventmanager.authentication.security.helper.PasswordEncoderHelper;
import com.utn.tacs.eventmanager.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private PasswordEncoder passwordEncoder = PasswordEncoderHelper.getBCryptPasswordEncoder();

	@Autowired
	private UserService userService;

	/**
	 * Personalizacion del metodo de autenticacion, autenticando por email, utilizando encoder que implementa
	 * un SALT random cada vez que encripta y genera un string alfanumerico de 60 caracteres. Si el usuario
	 * falla 3 veces seguidas al logearse, la cuenta se deshabilita.
	 * Al logearse correctamente se le devuelve el token que le corresponde.
	 * Si el usuario no existe, se devuelve un bad credentials exception.
	 * @param authentication
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		Optional<User> user = userService.readUserByEmail(username);
		if(!user.isPresent()) {
			throw new BadCredentialsException("Error trying to authenticate the user");
		}
		if(!passwordEncoder.matches(password,user.get().getPassword())) {
			user.get().addBadLogin();
			user.get().setLocked(Integer.valueOf(3).equals(user.get().getBadLogin()));
			userService.updateUser(user.get());
		}
		user.get().restoreBadLogin();
		userService.updateUser(user.get());
		Set<Permission> authorities = user.get().getRoles().stream()
				.flatMap(roleDTO -> roleDTO.getPermissions().stream())
				.collect(Collectors.toSet());
		return new UsernamePasswordAuthenticationToken(user.get().getEmail(), user.get().getPassword(), authorities);
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}
}
