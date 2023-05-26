package com.airline.infyAirline.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.airline.infyAirline.InfyAirlineApplication;
import com.airline.infyAirline.exception.InfyAirlineException;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class ExceptionControllerAdvice {
	@Autowired
	private Environment environment;


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorInfo> employeeExceptionHandler(Exception exception){
		ErrorInfo error= new ErrorInfo();
		error.setErrorMessage(exception.getMessage());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InfyAirlineException.class)
	public ResponseEntity<ErrorInfo> employeeExceptionHandler(InfyAirlineException employeeException){
		ErrorInfo error= new ErrorInfo();
		error.setErrorMessage(employeeException.getMessage());
		error.setErrorCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
	public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
		String errorMsg = "";
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException exception1 = (MethodArgumentNotValidException) exception;
			errorMsg = exception1.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(", "));
		} else {
			ConstraintViolationException exception2 = (ConstraintViolationException) exception;
			errorMsg = exception2.getConstraintViolations().stream().map(x -> x.getMessage())
					.collect(Collectors.joining(", "));
		}
		errorInfo.setErrorMessage(environment.getProperty(errorMsg));
		errorInfo.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}


}
