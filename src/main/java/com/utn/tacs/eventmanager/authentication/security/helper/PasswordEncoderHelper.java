package com.utn.tacs.eventmanager.authentication.security.helper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class PasswordEncoderHelper {
	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	public static PasswordEncoder getBCryptPasswordEncoder() {
		return passwordEncoder;
	}
}
