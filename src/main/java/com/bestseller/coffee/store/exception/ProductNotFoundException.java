package com.bestseller.coffee.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bestseller.coffee.store.request.ErrorCodes;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends CommonException {

	private static final long serialVersionUID = 8564073940322401192L;

	public ProductNotFoundException(Integer errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public ProductNotFoundException(ErrorCodes errorCode) {
		super(errorCode);
	}

}
