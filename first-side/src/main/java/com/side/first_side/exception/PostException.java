package com.side.first_side.exception;

public abstract class PostException extends RuntimeException {

	public PostException(String message) {
		super(message);

	}

	public PostException(String message, Throwable cause) {
		super(message, cause);

	}

	public abstract int getStatusCode();
}
