package com.side.first_side.request.post;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PostSearch {

	private final int MAX_SIZE = 2000;

	@Builder.Default
	private Integer page = 1;
	@Builder.Default
	private Integer size = 5;

	public long getOffset() {
		return (long)(Math.max(1, page)-1)  * Math.min(MAX_SIZE ,size);
	}
}
