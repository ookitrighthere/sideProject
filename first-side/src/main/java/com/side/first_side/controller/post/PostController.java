package com.side.first_side.controller.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.side.first_side.request.post.PostCreate;
import com.side.first_side.request.post.PostEdit;
import com.side.first_side.request.post.PostSearch;
import com.side.first_side.response.post.PostResponse;
import com.side.first_side.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/posts")
	public void posts(@RequestBody @Valid PostCreate postCreate) {
		postService.write(postCreate);
	}

	@GetMapping("/posts/{postId}")
	public PostResponse get(@PathVariable Long postId) {
		return postService.get(postId);
	}

	@GetMapping("/posts")
	public List<PostResponse> getList(PostSearch postSearch) {
		return postService.getList(postSearch);
	}

	@PatchMapping("/posts/{postId}")
	public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
		postService.edit(postId, postEdit);
	}
}
