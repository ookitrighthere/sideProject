package com.side.first_side.request.post;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostCreate {
	@NotBlank(message = "������ �Է��ϼ���.")
	private String title;
	@NotBlank(message = "������ �Է��ϼ���.")
	private String content;

	public PostCreate() {}

	@Builder
	public PostCreate(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
