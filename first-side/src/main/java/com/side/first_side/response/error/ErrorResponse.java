package com.side.first_side.response.error;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

/**
 * {
 * 	"code" : "400"
 *  "message" : "요청 값을 확인해주세요"
 *  "validation" : {
 *  	"fieldName" : "errorMessage"
 *     ,"title" : "제목을 입력해주세요."
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
