package com.side.first_side.response.error;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

/**
 * {
 * 	"code" : "400"
 *  "message" : "��û ���� Ȯ�����ּ���"
 *  "validation" : {
 *  	"fieldName" : "errorMessage"
 *     ,"title" : "������ �Է����ּ���."
 *   }
 * }
 */
@Getter
public class ErrorResponse {

	private final String code;
	private final String message;
	private final Map<String, String> validation;

	@Builder
	public ErrorResponse(String code, String message, Map<String, String> validation) {
		this.code = code;
		this.message = message;
		this.validation = validation != null ? validation : new HashMap<>();
	}

	public void addValidation(String code, String message) {
		this.validation.put(code, message);
	}
}
