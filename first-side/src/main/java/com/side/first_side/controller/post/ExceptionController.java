package com.side.first_side.controller.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.side.first_side.exception.InvalidRequest;
import com.side.first_side.exception.PostException;
import com.side.first_side.exception.PostNotFound;
import com.side.first_side.response.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionController {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse failVerification(MethodArgumentNotValidException e) {
		ErrorResponse errorResponse = ErrorResponse.builder()
													.code("400")
													.message("요청값을 확인해주세요.")
													.build();
		for(FieldError fieldError : e.getFieldErrors()) {
			errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());

		}
		return errorResponse;
	}

	@ResponseBody
	@ExceptionHandler(PostException.class)
	public ResponseEntity<ErrorResponse> postException(PostException e) {
		int statusCode = e.getStatusCode();
		ErrorResponse errorResponse = ErrorResponse.builder()
												   .code(String.valueOf(statusCode))
												   .message(e.getMessage())
												   .validation(e.getValidation())
												   .build();

		ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
															   .body(errorResponse);
		return response;
	}

}
