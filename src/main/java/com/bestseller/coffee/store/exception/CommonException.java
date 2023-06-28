package com.bestseller.coffee.store.exception;

import com.bestseller.coffee.store.request.ErrorCodes;

public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 4798384566065228547L;

	private final Integer errorCode;
	private final String errorMessage;

	public CommonException(ErrorCodes errorCode) {
		super(errorCode.getErrorMessage());
		this.errorCode = errorCode.getErrorCode();
		this.errorMessage = errorCode.getErrorMessage();
	}

	public CommonException(Integer errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
