package com.side.first_side.repository;

import static com.side.first_side.domain.QPost.post;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.side.first_side.domain.Post;
import com.side.first_side.request.post.PostSearch;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

	private final JPAQueryFactory jpaQueryFactory;


	@Override
	public List<Post> getList(PostSearch postSearch) {
		return jpaQueryFactory.selectFrom(post)
							  .limit(postSearch.getSize())
							  .offset(postSearch.getOffset())
							  .orderBy(post.id.desc())
							  .fetch();
	}
}
