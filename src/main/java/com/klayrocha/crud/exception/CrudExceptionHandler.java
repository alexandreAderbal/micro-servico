package com.klayrocha.crud.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CrudExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResorceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handlerBadRequestException(Exception exception,WebRequest request){
		
		ExceptionResponse exceptionResponse =
			ExceptionResponse.builder().timestamp(new Date())
										.message(exception.getMessage())
										.details(request.getDescription(false)).build();
		
		return ResponseEntity.badRequest().body(exceptionResponse);
		
	}

}
