package com.utn.tacs.eventmanager.authentication.security.helper;

import org.junit.Assert;
import org.junit.Test;

public class PasswordEncoderHelperTest {

	/**
	 * Test que prueba el metodo de encriptacion demostrando que un valor guardado previamente y un valor
	 * encriptado en tiempo de ejecucion matchean con la password que ingresamos.
	 */
	@Test
	public void encryptTest(){
		String rawPassword = "TACS-Grupo2-Password";
		String encriptedPassword = PasswordEncoderHelper.getBCryptPasswordEncoder().encode(rawPassword);
		String expectedValue = "$2a$10$YVy8s4yhqiS2IdoEfXek7.Kx8TsMPYRumE4AQv78JGrGOdu.5yySC";
		Assert.assertTrue(PasswordEncoderHelper.getBCryptPasswordEncoder().matches(rawPassword, encriptedPassword));
		Assert.assertTrue(PasswordEncoderHelper.getBCryptPasswordEncoder().matches(rawPassword, expectedValue));
	}
}
