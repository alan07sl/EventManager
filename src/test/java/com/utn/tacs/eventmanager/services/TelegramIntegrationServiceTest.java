package com.utn.tacs.eventmanager.services;

import com.utn.tacs.eventmanager.errors.InvalidCredentialsException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties="springBootApp.postConstructOff=true")
@AutoConfigureMockMvc
public class TelegramIntegrationServiceTest {

	@MockBean
	private EventbriteService eventbriteService;

	@MockBean
	private UserService userService;

	@MockBean
	private EventListService eventListService;

	@SpyBean
	private TelegramIntegrationService tis;

	private static Update updateMock = Mockito.mock(Update.class);
	private static Message messageMock = Mockito.mock(Message.class);

	@BeforeClass
	public static void setup(){
		when(updateMock.getMessage()).thenReturn(messageMock);
		when(messageMock.getChatId()).thenReturn(1L);
	}

	@Test
	public void correctLoginTest(){
		when(messageMock.getText()).thenReturn("/login test1 test2");
		doNothing().when(tis).mandarMensaje(ArgumentMatchers.anyString());

		tis.onUpdateReceived(updateMock);

		verify(tis,times(1)).mandarMensaje("Login Exitoso");
	}

	@Test
	public void invalidCredentialsTest() throws Exception{
		when(messageMock.getText()).thenReturn("/login test1 test3");
		when(userService.authenticateUser("test1","test3")).thenThrow(InvalidCredentialsException.class);
		doNothing().when(tis).mandarMensaje(ArgumentMatchers.anyString());

		tis.onUpdateReceived(updateMock);

		verify(tis,times(1)).mandarMensaje("Credenciales erroneas, por favor vuelva a ingresarlas");
	}

	@Test
	public void notEnoughParamsOnLoginTest() {
		when(messageMock.getText()).thenReturn("/login test1");
		doNothing().when(tis).mandarMensaje(ArgumentMatchers.anyString());

		tis.onUpdateReceived(updateMock);

		verify(tis,times(1)).mandarMensaje("Please set your username and password to login");
	}

}