package com.klayrocha.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResorceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -7848221053451561058L;

	public ResorceNotFoundException(String exception) {
		super(exception);
	}
	
}
