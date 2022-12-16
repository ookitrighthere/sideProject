package com.side.first_side.exception;

public class InvalidRequest extends PostException{

	static final String MESSAGE = "������ �ܾ ���ԵǾ��ֽ��ϴ�.";

	public InvalidRequest() {
		super(MESSAGE);
	}

	public InvalidRequest(String fieldName, String message) {
		super(MESSAGE);
		addValidation(fieldName, message);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}

}
