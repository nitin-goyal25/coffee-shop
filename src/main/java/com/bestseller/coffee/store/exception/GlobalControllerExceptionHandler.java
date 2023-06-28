package com.bestseller.coffee.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bestseller.coffee.store.request.ErrorCodes;
import com.bestseller.coffee.store.request.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String ERROR_TEXT = "Error while handling request, ERROR : {}";

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleProductNotFoundException(ProductNotFoundException ex) {
		return setErrorResponse(ex);
	}

	@ExceptionHandler(ToppingNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleToppingNotFoundException(ToppingNotFoundException ex) {
		return setErrorResponse(ex);

	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleToppingNotFoundException(Exception ex) {
		log.error(ERROR_TEXT, ex.getMessage(), ex);
		return new ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(),
				ErrorCodes.INTERNAL_SERVER_ERROR.getErrorMessage());
	}

	private ErrorResponse setErrorResponse(CommonException ex) {
		log.error(ERROR_TEXT, ex.getErrorMessage(), ex);
		return new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());

	}

}
