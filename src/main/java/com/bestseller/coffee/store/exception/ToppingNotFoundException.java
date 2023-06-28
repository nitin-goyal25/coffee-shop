package com.bestseller.coffee.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bestseller.coffee.store.request.ErrorCodes;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ToppingNotFoundException extends CommonException {

	private static final long serialVersionUID = 5097045710593760147L;

	public ToppingNotFoundException(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public ToppingNotFoundException(ErrorCodes errorCode) {
		super(errorCode);
	}
	
}
