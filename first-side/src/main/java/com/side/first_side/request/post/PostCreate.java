package com.side.first_side.request.post;

import javax.validation.constraints.NotBlank;

import com.side.first_side.exception.InvalidRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreate {
	@NotBlank(message = "제목을 입력하세요.")
	private String title;
	@NotBlank(message = "내용을 입력하세요.")
	private String content;

	public PostCreate() {}

	@Builder
	public PostCreate(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void isVaild() {
		if(title.contains("바보")) {
			throw new InvalidRequest("title","제목에 바보를 포함할 수 없습니다.");
		}
	}

}
