package com.side.first_side.exception;


public class PostNotFound extends PostException {

	static final String MESSAGE = "�������� �ʴ� ���Դϴ�.";

	public PostNotFound() {
		super(MESSAGE);
	}


	@Override
	public int getStatusCode() {
		return 404;
	}




}
