package com.side.first_side.exception;

public class InvalidRequest extends PostException{

	static final String MESSAGE = "������ �ܾ ���ԵǾ��ֽ��ϴ�.";

	public InvalidRequest() {
		super(MESSAGE);
	}

	@Override
	public int getStatusCode() {
		return 400;
	}

}
