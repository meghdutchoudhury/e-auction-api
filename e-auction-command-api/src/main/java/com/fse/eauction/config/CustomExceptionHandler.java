package com.fse.eauction.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fse.eauction.exception.BusinessException;
import com.fse.eauction.exception.ErrorResponse;
import com.fse.eauction.exception.ResourceNotFoundException;
import com.fse.eauction.exception.ResourceOperationNotPermittedException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, WebRequest request)  {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorResponse error = new ErrorResponse(UUID.randomUUID().toString(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request)  {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorResponse error = new ErrorResponse(UUID.randomUUID().toString(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceOperationNotPermittedException.class)
	public final ResponseEntity<ErrorResponse> handleResourceOperationNotPermittedException(ResourceOperationNotPermittedException ex, WebRequest request)  {
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorResponse error = new ErrorResponse(UUID.randomUUID().toString(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
}
