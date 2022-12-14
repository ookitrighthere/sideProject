package com.side.first_side.request.post;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEdit {

	@NotBlank(message = "제목을 입력해주세요.")
	private String title;

	@NotBlank(message = "내용을 입력해주세요.")
	private String content;

	public PostEdit() {}

	@Builder
	public PostEdit(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
