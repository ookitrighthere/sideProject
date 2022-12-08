package com.side.first_side.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.side.first_side.domain.Post;
import com.side.first_side.repository.PostRepository;
import com.side.first_side.request.post.PostCreate;
import com.side.first_side.response.post.PostResponse;

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

	public PostResponse get(Long postId) {
		Post post = postRepository.findById(postId)
								  .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));


		return PostResponse.builder()
						   .id(post.getId())
					       .title(post.getTitle())
					       .content(post.getContent())
					       .build();


	}

	public List<PostResponse> getList() {
		return postRepository.findAll().stream()
									   .map(post -> PostResponse.builder()
															    .id(post.getId())
															    .title(post.getTitle())
															    .content(post.getContent())
															    .build())
									   .collect(Collectors.toList());
	}
}
