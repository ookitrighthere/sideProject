package com.side.first_side.service;

import org.springframework.stereotype.Service;

import com.side.first_side.domain.Post;
import com.side.first_side.repository.PostRepository;
import com.side.first_side.request.post.PostCreate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	public final PostRepository postRepository;

	public void write(PostCreate postCreate) {
		Post post = Post.builder()
						.title(postCreate.getTitle())
						.content(postCreate.getContent())
						.build();
		postRepository.save(post);
	}

	public Post get(Long postId) {
		Post post = postRepository.findById(postId)
								  .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
		return post;
	}
}
