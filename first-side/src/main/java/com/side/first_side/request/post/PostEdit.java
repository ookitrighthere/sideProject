package com.side.first_side.request.post;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEdit {

	@NotBlank(message = "������ �Է����ּ���.")
	private String title;

	@NotBlank(message = "������ �Է����ּ���.")
	private String content;

	public PostEdit() {}

	@Builder
	public PostEdit(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
