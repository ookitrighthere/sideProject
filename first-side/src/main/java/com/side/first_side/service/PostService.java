package com.side.first_side.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.side.first_side.domain.Post;
import com.side.first_side.domain.PostEditor;
import com.side.first_side.exception.PostNotFound;
import com.side.first_side.repository.PostRepository;
import com.side.first_side.request.post.PostCreate;
import com.side.first_side.request.post.PostEdit;
import com.side.first_side.request.post.PostSearch;
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
								  .orElseThrow(() -> new PostNotFound());


		return PostResponse.builder()
						   .id(post.getId())
					       .title(post.getTitle())
					       .content(post.getContent())
					       .build();


	}

	public List<PostResponse> getList(PostSearch postSearch) {
		return postRepository.getList(postSearch).stream()
									   			 .map(post -> new PostResponse(post))
									             .collect(Collectors.toList());
	}

	@Transactional
	public void edit(Long id, PostEdit postEdit) {
		Post post = postRepository.findById(id)
					  			  .orElseThrow(() -> new PostNotFound());

		PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

		PostEditor postEditor = postEditorBuilder.title(postEdit.getTitle())
												 .content(postEdit.getContent())
												 .build();

		post.edit(postEditor);

	}

	public void delete(Long id) {
		Post post = postRepository.findById(id)
								  .orElseThrow(() -> new PostNotFound());
		postRepository.delete(post);
	}
}
