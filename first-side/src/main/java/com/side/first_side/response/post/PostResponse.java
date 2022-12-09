package com.side.first_side.response.post;

import com.side.first_side.domain.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

	private final Long id;
	private final String title;
	private final String content;


	public PostResponse(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
	}


	@Builder
	public PostResponse(Long id, String title, String content) {
		this.id = id;
		this.title = title.substring(0, Math.min(title.length(), 15));
		this.content = content;
	}


}
