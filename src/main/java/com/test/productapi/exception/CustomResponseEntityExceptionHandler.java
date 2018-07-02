package com.test.productapi.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom response exception handler aims at mapping exception types and their respective http error codes
 * 
 * @author Lazarin, Carlos
 *
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		return new ResponseEntity<Object>(
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(ResourceNotFoundException ex, WebRequest request)
			throws Exception {
		return new ResponseEntity<Object>(
				new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false)),
				HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>(
				new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString()),
				HttpStatus.BAD_REQUEST);
	}

}