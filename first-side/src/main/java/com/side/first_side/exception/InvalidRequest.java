package com.side.first_side.exception;

public class InvalidRequest extends PostException{

	static final String MESSAGE = "금지된 단어가 포함되어있습니다.";

	public InvalidRequest() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}

}
