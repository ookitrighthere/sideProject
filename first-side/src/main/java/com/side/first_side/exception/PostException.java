package com.side.first_side.exception;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class PostException extends RuntimeException {

	public final Map<String, String> validation = new HashMap<String, String>();

	public PostException(String message) {
		super(message);
	}

	public PostException(String message, Throwable cause) {
		super(message, cause);
	}

	public abstract int getStatusCode();

	public void addValidation(String fieldName, String message) {
		validation.put(fieldName, message);
	}
}
