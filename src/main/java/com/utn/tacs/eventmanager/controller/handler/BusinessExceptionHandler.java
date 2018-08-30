package com.utn.tacs.eventmanager.controller.handler;

import com.utn.tacs.eventmanager.exception.BusinessException;
import com.utn.tacs.eventmanager.exception.CustomError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Manejo de errores global A.K.A. Exception handler
 *
 * Beneficios de este approach:
 *
 * Permite control total tanto sobre el cuerpo de los response como el codigo de estado.
 * Permite mapear varias excepciones al mismo metodo para ser manejadas de manera conjunta.
 * Hace buen uso de la clase ResponseEntity
 *
 */
@RestControllerAdvice
public class BusinessExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	@org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
	public ResponseEntity<CustomError> handleBusinessException(BusinessException businessException) {
		return new ResponseEntity<>(new CustomError(businessException.getCode(),
				messageSource.getMessage(businessException.getMessage(), businessException.getMessageArguments(), Locale.getDefault()),
				messageSource.getMessage(businessException.getDescription(), businessException.getDescriptionArguments(), Locale.getDefault()),
				businessException.getStatus().value()), businessException.getStatus());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> onValidationError(BindException ex) {
		return onExceptionHandler(ex.getBindingResult());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> onValidationError(MethodArgumentNotValidException ex) {
		return onExceptionHandler(ex.getBindingResult());
	}

	private Map<String, String> onExceptionHandler(BindingResult bindingResult) {
		Map<String, String> errors = new HashMap<>();
		for (ObjectError error : bindingResult.getAllErrors()) {
			if (error instanceof FieldError) {
				errors.put(((FieldError) error).getField(), messageSource.getMessage(error, Locale.getDefault()));
			}
		}
		return errors;
	}
}
