package com.bestseller.coffee.store.request;

public enum ErrorCodes {

	PRODUCT_NOT_FOUND(1001, "Product with given Id does not exists"),
	TOPPING_NOT_FOUND(1002, "Topping with given Id does not exists"),
	INTERNAL_SERVER_ERROR(9999, "Internal Server Error");

	private Integer errorCode;
	private String errorMessage;

	ErrorCodes(Integer errorCode, String errorMessage) {
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
