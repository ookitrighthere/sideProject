package com.side.first_side.repository;

import java.util.List;

import com.side.first_side.domain.Post;
import com.side.first_side.request.post.PostSearch;

public interface PostRepositoryCustom {

	List<Post> getList(PostSearch postSearch);
}
