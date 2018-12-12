package br.com.alura.forum.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.forum.controller.dto.out.ValidationErrorDTO;

@RestControllerAdvice(basePackageClasses = {TopicController.class})
public class ValidationErrorHandler {
	@Autowired private MessageSource  messageSource;
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ValidationErrorDTO> tratarErroHibernate(MethodArgumentNotValidException e) {
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<ValidationErrorDTO> errors = fieldErrors.stream().map( field -> {
			return new ValidationErrorDTO(field.getField(), getMessageFromResource(field.getCode()));
		}).collect(Collectors.toList());
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UsernameNotFoundException.class)
	public ValidationErrorDTO problem() {
		return new ValidationErrorDTO("internal-server-error", getMessageFromResource("402"));
	}
	
	private String getMessageFromResource(String code) {
		return this.messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
	}
}
